package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.TongzhijiluEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.TongzhijiluView;

public interface TongzhijiluService extends IService<TongzhijiluEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TongzhijiluView> selectListView(Wrapper<TongzhijiluEntity> wrapper);
    
    TongzhijiluView selectView(@Param("ew") Wrapper<TongzhijiluEntity> wrapper);
    
    PageUtils queryPage(Map<String, Object> params, Wrapper<TongzhijiluEntity> wrapper);
    
    List<TongzhijiluEntity> selectListByStatus(String fasongzhuangtai);
    
    void updateRetryCount(Long id, Integer retryCount, String failReason);
    
    void updateSendStatus(Long id, String status);

}
