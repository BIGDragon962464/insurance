package com.czl.insurance.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import com.czl.insurance.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.czl.insurance.service.INoticeService;
import com.czl.insurance.entity.Notice;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Notice notice){
        if (notice.getId() == null){
            notice.setTime(DateUtil.today());
            notice.setUser(TokenUtils.getCurrentUser().getNickname());
        }
        noticeService.saveOrUpdate(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        noticeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(noticeService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(noticeService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(noticeService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}

