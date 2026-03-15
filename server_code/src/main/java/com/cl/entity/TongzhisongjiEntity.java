package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


@TableName("tongzhisongji")
public class TongzhisongjiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TongzhisongjiEntity() {
		
	}
	
	public TongzhisongjiEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String tongzhibianhao;
	
	private String yishengzhanghao;
	
	private String dianhua;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date jiuzhenshijian;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date songjishijian;
	
	private String zhanghao;
	
	private String shouji;
	
	private String songjizhuangtai;
	
	private Integer retrycount;
	
	private String shibaiyuanyin;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setTongzhibianhao(String tongzhibianhao) {
		this.tongzhibianhao = tongzhibianhao;
	}
	public String getTongzhibianhao() {
		return tongzhibianhao;
	}
	public void setYishengzhanghao(String yishengzhanghao) {
		this.yishengzhanghao = yishengzhanghao;
	}
	public String getYishengzhanghao() {
		return yishengzhanghao;
	}
	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}
	public String getDianhua() {
		return dianhua;
	}
	public void setJiuzhenshijian(Date jiuzhenshijian) {
		this.jiuzhenshijian = jiuzhenshijian;
	}
	public Date getJiuzhenshijian() {
		return jiuzhenshijian;
	}
	public void setSongjishijian(Date songjishijian) {
		this.songjishijian = songjishijian;
	}
	public Date getSongjishijian() {
		return songjishijian;
	}
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	public String getZhanghao() {
		return zhanghao;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	public String getShouji() {
		return shouji;
	}
	public void setSongjizhuangtai(String songjizhuangtai) {
		this.songjizhuangtai = songjizhuangtai;
	}
	public String getSongjizhuangtai() {
		return songjizhuangtai;
	}
	public void setRetrycount(Integer retrycount) {
		this.retrycount = retrycount;
	}
	public Integer getRetrycount() {
		return retrycount;
	}
	public void setShibaiyuanyin(String shibaiyuanyin) {
		this.shibaiyuanyin = shibaiyuanyin;
	}
	public String getShibaiyuanyin() {
		return shibaiyuanyin;
	}

}
