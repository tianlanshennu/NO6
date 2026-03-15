package com.cl.dao;

import com.cl.entity.TongzhijiluEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.TongzhijiluView;

public interface TongzhijiluDao extends BaseMapper<TongzhijiluEntity> {
	
	List<TongzhijiluView> selectListView(@Param("ew") Wrapper<TongzhijiluEntity> wrapper);

	List<TongzhijiluView> selectListView(Pagination page,@Param("ew") Wrapper<TongzhijiluEntity> wrapper);
	
	TongzhijiluView selectView(@Param("ew") Wrapper<TongzhijiluEntity> wrapper);

}
