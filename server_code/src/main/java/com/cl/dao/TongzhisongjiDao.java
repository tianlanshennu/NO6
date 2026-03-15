package com.cl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cl.entity.TongzhisongjiEntity;
import com.cl.entity.view.TongzhisongjiView;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


public interface TongzhisongjiDao extends BaseMapper<TongzhisongjiEntity> {

    List<TongzhisongjiView> selectListView(@Param("ew") com.baomidou.mybatisplus.mapper.Wrapper<TongzhisongjiEntity> wrapper);

    List<TongzhisongjiView> selectView(@Param("ew") com.baomidou.mybatisplus.mapper.Wrapper<TongzhisongjiEntity> wrapper);
}
