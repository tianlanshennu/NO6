package com.cl.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;


import com.cl.dao.TongzhisongjiDao;
import com.cl.entity.TongzhisongjiEntity;
import com.cl.service.TongzhisongjiService;
import com.cl.entity.view.TongzhisongjiView;

@Service("tongzhisongjiService")
public class TongzhisongjiServiceImpl extends ServiceImpl<TongzhisongjiDao, TongzhisongjiEntity> implements TongzhisongjiService {

    	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TongzhisongjiEntity> page = this.selectPage(
                new Query<TongzhisongjiEntity>(params).getPage(),
                new EntityWrapper<TongzhisongjiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<TongzhisongjiEntity> wrapper) {
		  Page<TongzhisongjiView> page =new Query<TongzhisongjiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<TongzhisongjiView> selectListView(Wrapper<TongzhisongjiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public TongzhisongjiView selectView(Wrapper<TongzhisongjiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
	
	@Override
	@Transactional
	public void retryFailedNotification(Long id) {
		TongzhisongjiEntity notification = this.selectById(id);
		if (notification != null && "失败".equals(notification.getSongjizhuangtai())) {
			Integer retryCount = notification.getRetrycount();
			if (retryCount == null) {
				retryCount = 0;
			}
			
			if (retryCount >= 3) {
				throw new RuntimeException("重试次数已达上限，请管理员介入处理");
			}
			
			notification.setRetrycount(retryCount + 1);
			notification.setSongjishijian(new Date());
			notification.setSongjizhuangtai("发送中");
			
			this.updateById(notification);
			
			try {
				sendNotification(notification);
				
				notification.setSongjizhuangtai("成功");
				this.updateById(notification);
			} catch (Exception e) {
				notification.setSongjizhuangtai("失败");
				notification.setShibaiyuanyin(e.getMessage());
				this.updateById(notification);
				throw new RuntimeException("重试失败：" + e.getMessage());
			}
		} else {
			throw new RuntimeException("该通知无需重试");
		}
	}
	
	@Override
	@Transactional
	public void markAsProcessed(Long id) {
		TongzhisongjiEntity notification = this.selectById(id);
		if (notification != null) {
			notification.setSongjizhuangtai("已处理");
			this.updateById(notification);
		}
	}
	
	private void sendNotification(TongzhisongjiEntity notification) {
		String shouji = notification.getShouji();
		if (shouji == null || shouji.trim().isEmpty()) {
			throw new RuntimeException("手机号不能为空");
		}
		
		String content = String.format("【医院通知】尊敬的患者，您已成功预约医生。就诊时间：%s，医生：%s，电话：%s。请按时就诊。", 
			notification.getJiuzhenshijian(), 
			notification.getYishengzhanghao(),
			notification.getDianhua());
		
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
