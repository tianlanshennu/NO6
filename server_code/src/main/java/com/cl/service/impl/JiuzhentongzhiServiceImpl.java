package com.cl.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.Date;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;


import com.cl.dao.JiuzhentongzhiDao;
import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.service.JiuzhentongzhiService;
import com.cl.entity.view.JiuzhentongzhiView;
import com.cl.entity.TongzhisongjiEntity;
import com.cl.service.TongzhisongjiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service("jiuzhentongzhiService")
public class JiuzhentongzhiServiceImpl extends ServiceImpl<JiuzhentongzhiDao, JiuzhentongzhiEntity> implements JiuzhentongzhiService {

    @Autowired
    private TongzhisongjiService tongzhisongjiService;
    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JiuzhentongzhiEntity> page = this.selectPage(
                new Query<JiuzhentongzhiEntity>(params).getPage(),
                new EntityWrapper<JiuzhentongzhiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JiuzhentongzhiEntity> wrapper) {
		  Page<JiuzhentongzhiView> page =new Query<JiuzhentongzhiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<JiuzhentongzhiView> selectListView(Wrapper<JiuzhentongzhiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JiuzhentongzhiView selectView(Wrapper<JiuzhentongzhiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	@Override
	@Transactional
	public void sendNotificationImmediately(JiuzhentongzhiEntity jiuzhentongzhi) {
		if (jiuzhentongzhi == null) {
			throw new RuntimeException("就诊通知不能为空");
		}
		
		if (jiuzhentongzhi.getTongzhibianhao() == null || jiuzhentongzhi.getTongzhibianhao().trim().isEmpty()) {
			jiuzhentongzhi.setTongzhibianhao(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
		}
		
		if (jiuzhentongzhi.getTongzhishijian() == null) {
			jiuzhentongzhi.setTongzhishijian(new Date());
		}
		
		this.insert(jiuzhentongzhi);
		
		TongzhisongjiEntity songjiEntity = new TongzhisongjiEntity();
		songjiEntity.setTongzhibianhao(jiuzhentongzhi.getTongzhibianhao());
		songjiEntity.setYishengzhanghao(jiuzhentongzhi.getYishengzhanghao());
		songjiEntity.setDianhua(jiuzhentongzhi.getDianhua());
		songjiEntity.setJiuzhenshijian(jiuzhentongzhi.getJiuzhenshijian());
		songjiEntity.setSongjishijian(new Date());
		songjiEntity.setZhanghao(jiuzhentongzhi.getZhanghao());
		songjiEntity.setShouji(jiuzhentongzhi.getShouji());
		songjiEntity.setSongjizhuangtai("发送中");
		songjiEntity.setRetrycount(0);
		
		tongzhisongjiService.insert(songjiEntity);
		
		try {
			sendSmsNotification(songjiEntity);
			
			songjiEntity.setSongjizhuangtai("成功");
			tongzhisongjiService.updateById(songjiEntity);
		} catch (Exception e) {
			songjiEntity.setSongjizhuangtai("失败");
			songjiEntity.setShibaiyuanyin(e.getMessage());
			tongzhisongjiService.updateById(songjiEntity);
			
			throw new RuntimeException("通知发送失败：" + e.getMessage());
		}
	}
	
	private void sendSmsNotification(TongzhisongjiEntity notification) {
		String shouji = notification.getShouji();
		if (shouji == null || shouji.trim().isEmpty()) {
			throw new RuntimeException("手机号不能为空");
		}
		
		String content = String.format("【医院通知】尊敬的患者，您已成功预约医生。就诊时间：%s，医生：%s，电话：%s。请按时就诊。", 
			notification.getJiuzhenshijian() != null ? notification.getJiuzhenshijian().toString() : "未定",
			notification.getYishengzhanghao() != null ? notification.getYishengzhanghao() : "未知",
			notification.getDianhua() != null ? notification.getDianhua() : "未知");
		
		boolean success = sendSms(shouji, content);
		
		if (!success) {
			throw new RuntimeException("短信发送失败");
		}
	}
	
	private boolean sendSms(String phone, String content) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
