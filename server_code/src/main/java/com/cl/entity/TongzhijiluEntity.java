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

@TableName("tongzhijilu")
public class TongzhijiluEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public TongzhijiluEntity() {
		
	}
	
	public TongzhijiluEntity(T t) {
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
	private Date tongzhishijian;
	
	private String zhanghao;
	
	private String shouji;
	
	private String tongzhileixing;
	
	private String tongzhineirong;
	
	private String fasongzhuangtai;
	
	private Integer chongshicishu;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date chongshishijian;
	
	private String shibaiyuanyin;
	
	private String chulizhuangtai;
	
	private String chulibeizhu;
	
	private Long yuyueid;

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
	
	public String getTongzhibianhao() {
		return tongzhibianhao;
	}
	public void setTongzhibianhao(String tongzhibianhao) {
		this.tongzhibianhao = tongzhibianhao;
	}
	
	public String getYishengzhanghao() {
		return yishengzhanghao;
	}
	public void setYishengzhanghao(String yishengzhanghao) {
		this.yishengzhanghao = yishengzhanghao;
	}
	
	public String getDianhua() {
		return dianhua;
	}
	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}
	
	public Date getJiuzhenshijian() {
		return jiuzhenshijian;
	}
	public void setJiuzhenshijian(Date jiuzhenshijian) {
		this.jiuzhenshijian = jiuzhenshijian;
	}
	
	public Date getTongzhishijian() {
		return tongzhishijian;
	}
	public void setTongzhishijian(Date tongzhishijian) {
		this.tongzhishijian = tongzhishijian;
	}
	
	public String getZhanghao() {
		return zhanghao;
	}
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	
	public String getShouji() {
		return shouji;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	
	public String getTongzhileixing() {
		return tongzhileixing;
	}
	public void setTongzhileixing(String tongzhileixing) {
		this.tongzhileixing = tongzhileixing;
	}
	
	public String getTongzhineirong() {
		return tongzhineirong;
	}
	public void setTongzhineirong(String tongzhineirong) {
		this.tongzhineirong = tongzhineirong;
	}
	
	public String getFasongzhuangtai() {
		return fasongzhuangtai;
	}
	public void setFasongzhuangtai(String fasongzhuangtai) {
		this.fasongzhuangtai = fasongzhuangtai;
	}
	
	public Integer getChongshicishu() {
		return chongshicishu;
	}
	public void setChongshicishu(Integer chongshicishu) {
		this.chongshicishu = chongshicishu;
	}
	
	public Date getChongshishijian() {
		return chongshishijian;
	}
	public void setChongshishijian(Date chongshishijian) {
		this.chongshishijian = chongshishijian;
	}
	
	public String getShibaiyuanyin() {
		return shibaiyuanyin;
	}
	public void setShibaiyuanyin(String shibaiyuanyin) {
		this.shibaiyuanyin = shibaiyuanyin;
	}
	
	public String getChulizhuangtai() {
		return chulizhuangtai;
	}
	public void setChulizhuangtai(String chulizhuangtai) {
		this.chulizhuangtai = chulizhuangtai;
	}
	
	public String getChulibeizhu() {
		return chulibeizhu;
	}
	public void setChulibeizhu(String chulibeizhu) {
		this.chulibeizhu = chulibeizhu;
	}
	
	public Long getYuyueid() {
		return yuyueid;
	}
	public void setYuyueid(Long yuyueid) {
		this.yuyueid = yuyueid;
	}

}
