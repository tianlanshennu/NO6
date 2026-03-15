package com.cl.entity.view;

import com.cl.entity.TongzhisongjiEntity;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.lang3.StringUtils;

@TableName("tongzhisongji")
public class TongzhisongjiView extends TongzhisongjiEntity {
	
	public TongzhisongjiView() {
	}
	
	public TongzhisongjiView(TongzhisongjiEntity tongzhisongjiEntity) {
		try {
			BeanUtils.copyProperties(this, tongzhisongjiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
