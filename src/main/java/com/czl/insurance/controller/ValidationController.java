package com.czl.insurance.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.czl.insurance.service.IValidationService;
import com.czl.insurance.entity.Validation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/validation")
public class ValidationController {

    @Resource
    private IValidationService validationService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Validation validation){
        validationService.saveOrUpdate(validation);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        validationService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        validationService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(validationService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(validationService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Validation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(validationService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}

