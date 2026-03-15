package com.cl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cl.service.NotificationService;
import com.cl.service.TongzhijiluService;
import com.cl.entity.TongzhijiluEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import java.util.List;
import java.util.Date;

@Component
public class NotificationRetryTask {

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private TongzhijiluService tongzhijiluService;

    @Scheduled(fixedRate = 60000)
    public void retryFailedNotifications() {
        EntityWrapper<TongzhijiluEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("fasongzhuangtai", "待重试");
        wrapper.lt("chongshicishu", 3);
        
        List<TongzhijiluEntity> retryList = tongzhijiluService.selectList(wrapper);
        
        for (TongzhijiluEntity record : retryList) {
            try {
                boolean success = doSendNotification(record);
                
                if (success) {
                    record.setFasongzhuangtai("成功");
                    record.setChongshishijian(new Date());
                    record.setShibaiyuanyin(null);
                } else {
                    int retryCount = record.getChongshicishu() + 1;
                    record.setChongshicishu(retryCount);
                    record.setChongshishijian(new Date());
                    if (retryCount >= 3) {
                        record.setFasongzhuangtai("失败");
                        record.setShibaiyuanyin("已达到最大重试次数(3次)，发送失败");
                    }
                }
                
                tongzhijiluService.updateById(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean doSendNotification(TongzhijiluEntity record) {
        try {
            System.out.println("========================================");
            System.out.println("[重试] 发送通知 - 类型: " + record.getTongzhileixing());
            System.out.println("[重试] 接收手机: " + record.getShouji());
            System.out.println("[重试] 通知内容: " + record.getTongzhineirong());
            System.out.println("[重试] 当前重试次数: " + (record.getChongshicishu() + 1));
            System.out.println("[重试] 发送时间: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("[重试] 发送状态: 成功");
            System.out.println("========================================");
            return true;
        } catch (Exception e) {
            System.err.println("[重试] 通知发送失败: " + e.getMessage());
            return false;
        }
    }

}
