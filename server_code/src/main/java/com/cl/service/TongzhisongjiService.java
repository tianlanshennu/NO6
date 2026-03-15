package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.TongzhisongjiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.TongzhisongjiView;


public interface TongzhisongjiService extends IService<TongzhisongjiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<TongzhisongjiView> selectListView(Wrapper<TongzhisongjiEntity> wrapper);
   	
   	TongzhisongjiView selectView(@Param("ew") Wrapper<TongzhisongjiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<TongzhisongjiEntity> wrapper);
   	
   	void retryFailedNotification(Long id);
   	
   	void markAsProcessed(Long id);
}
