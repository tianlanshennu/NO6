package com.cl.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.YishengyuyueEntity;
import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.dao.TongzhijiluDao;
import com.cl.dao.JiuzhentongzhiDao;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Service
public class NotificationService {

    @Autowired
    private TongzhijiluDao tongzhijiluDao;
    
    @Autowired
    private JiuzhentongzhiDao jiuzhentongzhiDao;

    private static final int MAX_RETRY_COUNT = 3;
    
    private static final String[] NOTIFICATION_TYPES = {
        "预约成功通知",
        "就诊前1天提醒",
        "就诊前2小时提醒",
        "就诊前30分钟提醒"
    };

    @Transactional
    public void sendAllNotifications(YishengyuyueEntity yuyue) {
        Date now = new Date();
        Date jiuzhenTime = yuyue.getYuyueshijian();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String jiuzhenTimeStr = sdf.format(jiuzhenTime);
        
        for (String type : NOTIFICATION_TYPES) {
            String content = generateNotificationContent(type, yuyue, jiuzhenTimeStr);
            Date notifyTime = calculateNotifyTime(type, jiuzhenTime, now);
            
            sendNotification(yuyue, type, content, notifyTime, now);
        }
    }
    
    private String generateNotificationContent(String type, YishengyuyueEntity yuyue, String jiuzhenTimeStr) {
        StringBuilder content = new StringBuilder();
        content.append("尊敬的").append(yuyue.getZhanghao()).append("用户，您好！\n");
        
        switch (type) {
            case "预约成功通知":
                content.append("您已成功预约医生【").append(yuyue.getYishengzhanghao()).append("】\n");
                content.append("预约时间：").append(jiuzhenTimeStr).append("\n");
                content.append("请按时就诊，如有变动请及时联系。");
                break;
            case "就诊前1天提醒":
                content.append("温馨提醒：您预约的医生【").append(yuyue.getYishengzhanghao()).append("】\n");
                content.append("就诊时间为明天：").append(jiuzhenTimeStr).append("\n");
                content.append("请提前做好准备，按时就诊。");
                break;
            case "就诊前2小时提醒":
                content.append("就诊提醒：您预约的医生【").append(yuyue.getYishengzhanghao()).append("】\n");
                content.append("就诊时间为2小时后：").append(jiuzhenTimeStr).append("\n");
                content.append("请提前到达医院，做好就诊准备。");
                break;
            case "就诊前30分钟提醒":
                content.append("紧急提醒：您预约的医生【").append(yuyue.getYishengzhanghao()).append("】\n");
                content.append("就诊时间为30分钟后：").append(jiuzhenTimeStr).append("\n");
                content.append("请立即前往诊室候诊。");
                break;
        }
        return content.toString();
    }
    
    private Date calculateNotifyTime(String type, Date jiuzhenTime, Date now) {
        long jiuzhenMillis = jiuzhenTime.getTime();
        long notifyMillis;
        
        switch (type) {
            case "预约成功通知":
                return now;
            case "就诊前1天提醒":
                notifyMillis = jiuzhenMillis - 24 * 60 * 60 * 1000;
                break;
            case "就诊前2小时提醒":
                notifyMillis = jiuzhenMillis - 2 * 60 * 60 * 1000;
                break;
            case "就诊前30分钟提醒":
                notifyMillis = jiuzhenMillis - 30 * 60 * 1000;
                break;
            default:
                return now;
        }
        
        if (notifyMillis < now.getTime()) {
            return now;
        }
        return new Date(notifyMillis);
    }
    
    private void sendNotification(YishengyuyueEntity yuyue, String type, String content, Date notifyTime, Date now) {
        TongzhijiluEntity record = new TongzhijiluEntity();
        record.setTongzhibianhao(generateNotificationNumber());
        record.setYishengzhanghao(yuyue.getYishengzhanghao());
        record.setDianhua(yuyue.getDianhua());
        record.setJiuzhenshijian(yuyue.getYuyueshijian());
        record.setTongzhishijian(notifyTime);
        record.setZhanghao(yuyue.getZhanghao());
        record.setShouji(yuyue.getShouji());
        record.setTongzhileixing(type);
        record.setTongzhineirong(content);
        record.setYuyueid(yuyue.getId());
        record.setChongshicishu(0);
        record.setChulizhuangtai("未处理");
        record.setAddtime(now);
        
        boolean sendSuccess = doSendNotification(yuyue.getShouji(), content, type);
        
        if (sendSuccess) {
            record.setFasongzhuangtai("成功");
            record.setChongshishijian(now);
        } else {
            record.setFasongzhuangtai("失败");
            record.setShibaiyuanyin("通知发送失败，请检查联系方式");
            record.setChongshishijian(now);
        }
        
        tongzhijiluDao.insert(record);
        
        if (sendSuccess && notifyTime.equals(now)) {
            JiuzhentongzhiEntity tongzhi = new JiuzhentongzhiEntity();
            tongzhi.setTongzhibianhao(generateNotificationNumber());
            tongzhi.setYishengzhanghao(yuyue.getYishengzhanghao());
            tongzhi.setDianhua(yuyue.getDianhua());
            tongzhi.setJiuzhenshijian(yuyue.getYuyueshijian());
            tongzhi.setTongzhishijian(now);
            tongzhi.setZhanghao(yuyue.getZhanghao());
            tongzhi.setShouji(yuyue.getShouji());
            tongzhi.setTongzhibeizhu(type + ": " + content);
            tongzhi.setAddtime(now);
            jiuzhentongzhiDao.insert(tongzhi);
        }
    }
    
    private boolean doSendNotification(String phone, String content, String type) {
        try {
            System.out.println("========================================");
            System.out.println("发送通知 - 类型: " + type);
            System.out.println("接收手机: " + phone);
            System.out.println("通知内容: " + content);
            System.out.println("发送时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            System.out.println("发送状态: 成功");
            System.out.println("========================================");
            return true;
        } catch (Exception e) {
            System.err.println("通知发送失败: " + e.getMessage());
            return false;
        }
    }
    
    private String generateNotificationNumber() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    @Transactional
    public void retryFailedNotifications() {
        Wrapper<TongzhijiluEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("fasongzhuangtai", "待重试");
        wrapper.lt("chongshicishu", MAX_RETRY_COUNT);
        
        List<TongzhijiluEntity> failedList = tongzhijiluDao.selectList(wrapper);
        
        for (TongzhijiluEntity record : failedList) {
            int retryCount = record.getChongshicishu() + 1;
            boolean success = doSendNotification(record.getShouji(), record.getTongzhineirong(), record.getTongzhileixing());
            
            if (success) {
                record.setFasongzhuangtai("成功");
                record.setChongshishijian(new Date());
                record.setShibaiyuanyin(null);
            } else {
                record.setChongshicishu(retryCount);
                record.setChongshishijian(new Date());
                if (retryCount >= MAX_RETRY_COUNT) {
                    record.setFasongzhuangtai("失败");
                    record.setShibaiyuanyin("已达到最大重试次数(" + MAX_RETRY_COUNT + "次)，发送失败");
                }
            }
            
            tongzhijiluDao.updateById(record);
        }
    }

}
