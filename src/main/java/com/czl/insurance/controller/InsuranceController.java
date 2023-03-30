package com.czl.insurance.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.czl.insurance.service.IInsuranceService;
import com.czl.insurance.entity.Insurance;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Resource
    private IInsuranceService insuranceService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Insurance insurance){
        insuranceService.saveOrUpdate(insurance);
        return Result.success();
    }

    @PostMapping("/buyInsurance/{userId}/{insuranceId}")
    public Result buyInsurance(@PathVariable Integer userId ,@PathVariable Integer insuranceId){
        insuranceService.setBuyInsurance(userId,insuranceId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        insuranceService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        insuranceService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(insuranceService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(insuranceService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam String types,
                                    @RequestParam String name,
                                    //@RequestParam Integer price,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("name",name);
        queryWrapper.like("types",types);
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}

