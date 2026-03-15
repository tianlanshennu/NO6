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

import com.cl.entity.TongzhisongjiEntity;
import com.cl.entity.view.TongzhisongjiView;

import com.cl.service.TongzhisongjiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

@RestController
@RequestMapping("/tongzhisongji")
public class TongzhisongjiController {
    @Autowired
    private TongzhisongjiService tongzhisongjiService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, TongzhisongjiEntity tongzhisongji,
                                                                                                                                            HttpServletRequest request){
        EntityWrapper<TongzhisongjiEntity> ew = new EntityWrapper<TongzhisongjiEntity>();
                                                                                                                                                                                                                                
        
        
        PageUtils page = tongzhisongjiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhisongji), params), params));
        return R.ok().put("data", page);
    }

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TongzhisongjiEntity tongzhisongji,
		HttpServletRequest request){
        EntityWrapper<TongzhisongjiEntity> ew = new EntityWrapper<TongzhisongjiEntity>();

		PageUtils page = tongzhisongjiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhisongji), params), params));
        return R.ok().put("data", page);
    }

	@RequestMapping("/lists")
    public R list( TongzhisongjiEntity tongzhisongji){
       	EntityWrapper<TongzhisongjiEntity> ew = new EntityWrapper<TongzhisongjiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tongzhisongji, "tongzhisongji")); 
        return R.ok().put("data", tongzhisongjiService.selectListView(ew));
    }

	@RequestMapping("/query")
    public R query(TongzhisongjiEntity tongzhisongji){
        EntityWrapper< TongzhisongjiEntity> ew = new EntityWrapper< TongzhisongjiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tongzhisongji, "tongzhisongji")); 
		TongzhisongjiView tongzhisongjiView =  tongzhisongjiService.selectView(ew);
		return R.ok("查询通知发送记录成功").put("data", tongzhisongjiView);
    }
	
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TongzhisongjiEntity tongzhisongji = tongzhisongjiService.selectById(id);
		tongzhisongji = tongzhisongjiService.selectView(new EntityWrapper<TongzhisongjiEntity>().eq("id", id));
        return R.ok().put("data", tongzhisongji);
    }

    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TongzhisongjiEntity tongzhisongji = tongzhisongjiService.selectById(id);
		tongzhisongji = tongzhisongjiService.selectView(new EntityWrapper<TongzhisongjiEntity>().eq("id", id));
        return R.ok().put("data", tongzhisongji);
    }
    
    @RequestMapping("/save")
    @SysLog("新增通知发送记录")
    public R save(@RequestBody TongzhisongjiEntity tongzhisongji, HttpServletRequest request){
    	tongzhisongjiService.insert(tongzhisongji);
        return R.ok();
    }
    
    @SysLog("新增通知发送记录")
    @RequestMapping("/add")
    public R add(@RequestBody TongzhisongjiEntity tongzhisongji, HttpServletRequest request){
    	tongzhisongjiService.insert(tongzhisongji);
        return R.ok();
    }

    @RequestMapping("/update")
    @Transactional
    @SysLog("修改通知发送记录")
    public R update(@RequestBody TongzhisongjiEntity tongzhisongji, HttpServletRequest request){
        tongzhisongjiService.updateById(tongzhisongji);
        return R.ok();
    }

    @RequestMapping("/delete")
    @SysLog("删除通知发送记录")
    public R delete(@RequestBody Long[] ids){
        tongzhisongjiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    @RequestMapping("/retry/{id}")
    @Transactional
    @SysLog("重试发送通知")
    public R retry(@PathVariable("id") Long id){
        try {
            tongzhisongjiService.retryFailedNotification(id);
            return R.ok("重试成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @RequestMapping("/batchRetry")
    @Transactional
    @SysLog("批量重试发送失败的通知")
    public R batchRetry(@RequestBody Long[] ids){
        int successCount = 0;
        int failCount = 0;
        StringBuilder errorMsg = new StringBuilder();
        
        for (Long id : ids) {
            try {
                tongzhisongjiService.retryFailedNotification(id);
                successCount++;
            } catch (Exception e) {
                failCount++;
                errorMsg.append("ID:").append(id).append(":").append(e.getMessage()).append("; ");
            }
        }
        
        return R.ok("重试完成，成功：" + successCount + "，失败：" + failCount).put("error", errorMsg.toString());
    }

    @RequestMapping("/markProcessed/{id}")
    @Transactional
    @SysLog("标记通知为已处理")
    public R markProcessed(@PathVariable("id") Long id){
        tongzhisongjiService.markAsProcessed(id);
        return R.ok("已标记为已处理");
    }

    @RequestMapping("/statistics")
    public R statistics(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        
        long totalCount = tongzhisongjiService.selectCount(new EntityWrapper<TongzhisongjiEntity>());
        long successCount = tongzhisongjiService.selectCount(new EntityWrapper<TongzhisongjiEntity>().eq("songjizhuangtai", "成功"));
        long failCount = tongzhisongjiService.selectCount(new EntityWrapper<TongzhisongjiEntity>().eq("songjizhuangtai", "失败"));
        long processingCount = tongzhisongjiService.selectCount(new EntityWrapper<TongzhisongjiEntity>().eq("songjizhuangtai", "发送中"));
        
        result.put("total", totalCount);
        result.put("success", successCount);
        result.put("fail", failCount);
        result.put("processing", processingCount);
        result.put("successRate", totalCount > 0 ? String.format("%.2f", (double)successCount/totalCount*100) + "%" : "0%");
        
        return R.ok().put("data", result);
    }
}
