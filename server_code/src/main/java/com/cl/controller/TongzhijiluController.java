package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.annotation.SysLog;

import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.view.TongzhijiluView;

import com.cl.service.TongzhijiluService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

@RestController
@RequestMapping("/tongzhijilu")
public class TongzhijiluController {
    @Autowired
    private TongzhijiluService tongzhijiluService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TongzhijiluEntity tongzhijilu,
                                                                                                                                                    HttpServletRequest request){
        EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();
        PageUtils page = tongzhijiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijilu), params), params));
        return R.ok().put("data", page);
    }

	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TongzhijiluEntity tongzhijilu,
		HttpServletRequest request){
        EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();
		PageUtils page = tongzhijiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijilu), params), params));
        return R.ok().put("data", page);
    }

    @RequestMapping("/lists")
    public R list( TongzhijiluEntity tongzhijilu){
       	EntityWrapper<TongzhijiluEntity> ew = new EntityWrapper<TongzhijiluEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tongzhijilu, "tongzhijilu")); 
        return R.ok().put("data", tongzhijiluService.selectListView(ew));
    }

    @RequestMapping("/query")
    public R query(TongzhijiluEntity tongzhijilu){
        EntityWrapper< TongzhijiluEntity> ew = new EntityWrapper< TongzhijiluEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tongzhijilu, "tongzhijilu")); 
		TongzhijiluView tongzhijiluView =  tongzhijiluService.selectView(ew);
		return R.ok("查询通知记录成功").put("data", tongzhijiluView);
    }
	
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TongzhijiluEntity tongzhijilu = tongzhijiluService.selectById(id);
		tongzhijilu = tongzhijiluService.selectView(new EntityWrapper<TongzhijiluEntity>().eq("id", id));
        return R.ok().put("data", tongzhijilu);
    }

	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TongzhijiluEntity tongzhijilu = tongzhijiluService.selectById(id);
		tongzhijilu = tongzhijiluService.selectView(new EntityWrapper<TongzhijiluEntity>().eq("id", id));
        return R.ok().put("data", tongzhijilu);
    }

    @RequestMapping("/save")
    @SysLog("新增通知记录")
    public R save(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
        tongzhijiluService.insert(tongzhijilu);
        return R.ok();
    }

    @RequestMapping("/update")
    @Transactional
    @SysLog("修改通知记录")
    public R update(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
        tongzhijiluService.updateById(tongzhijilu);
        return R.ok();
    }

    @RequestMapping("/delete")
    @SysLog("删除通知记录")
    public R delete(@RequestBody Long[] ids){
        tongzhijiluService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    @RequestMapping("/chuli")
    @SysLog("处理通知记录")
    public R chuli(@RequestBody TongzhijiluEntity tongzhijilu, HttpServletRequest request){
        tongzhijilu.setChulizhuangtai("已处理");
        tongzhijiluService.updateById(tongzhijilu);
        return R.ok();
    }
    
    @RequestMapping("/retry")
    @SysLog("重试发送通知")
    public R retry(@RequestBody Long[] ids, HttpServletRequest request){
        List<TongzhijiluEntity> list = tongzhijiluService.selectBatchIds(Arrays.asList(ids));
        for(TongzhijiluEntity entity : list) {
            if("失败".equals(entity.getFasongzhuangtai()) && entity.getChongshicishu() < 3) {
                entity.setFasongzhuangtai("待重试");
                tongzhijiluService.updateById(entity);
            }
        }
        return R.ok();
    }
    
    @RequestMapping("/countByStatus")
    public R countByStatus(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        EntityWrapper<TongzhijiluEntity> ewSuccess = new EntityWrapper<TongzhijiluEntity>().eq("fasongzhuangtai", "成功");
        EntityWrapper<TongzhijiluEntity> ewFail = new EntityWrapper<TongzhijiluEntity>().eq("fasongzhuangtai", "失败");
        EntityWrapper<TongzhijiluEntity> ewPending = new EntityWrapper<TongzhijiluEntity>().eq("fasongzhuangtai", "待重试");
        result.put("success", tongzhijiluService.selectCount(ewSuccess));
        result.put("fail", tongzhijiluService.selectCount(ewFail));
        result.put("pending", tongzhijiluService.selectCount(ewPending));
        return R.ok().put("data", result);
    }

}
