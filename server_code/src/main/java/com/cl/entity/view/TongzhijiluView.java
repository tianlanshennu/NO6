package com.cl.entity.view;

import com.cl.entity.TongzhijiluEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;

@TableName("tongzhijilu")
public class TongzhijiluView extends TongzhijiluEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public TongzhijiluView() {
		
	}
	
	public TongzhijiluView(TongzhijiluEntity tongzhijiluEntity) {
		try {
			BeanUtils.copyProperties(this, tongzhijiluEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
