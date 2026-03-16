package com.cl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cl.entity.TongzhisongjiEntity;
import com.cl.entity.view.TongzhisongjiView;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


public interface TongzhisongjiDao extends BaseMapper<TongzhisongjiEntity> {

    List<TongzhisongjiView> selectListView(@Param("ew") Wrapper<TongzhisongjiEntity> wrapper);

    List<TongzhisongjiView> selectListView(Pagination page,@Param("ew") Wrapper<TongzhisongjiEntity> wrapper);

    TongzhisongjiView selectView(@Param("ew") Wrapper<TongzhisongjiEntity> wrapper);
}
