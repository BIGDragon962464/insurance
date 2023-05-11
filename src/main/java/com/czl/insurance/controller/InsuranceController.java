package com.czl.insurance.controller;


import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
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

    @DeleteMapping("deleteOrder/{userId}/{insuranceId}")
    public Result deleteBuyInsurance(@PathVariable Integer userId ,@PathVariable Integer insuranceId) {
        insuranceService.deleteBuyInsurance(userId,insuranceId);
        return Result.success();
    }

    @GetMapping("findInsurance/{id}")
    public Result findInsurance(@PathVariable Integer id){
        List<Insurance> list = insuranceService.findInsurance(id);
        return Result.success(list);
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

    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                            @RequestParam String name,
                            @RequestParam Integer price,
                            @RequestParam String types,
                            @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("price",price);
        queryWrapper.like("name",name);
        queryWrapper.like("types",types);
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/renshen")
    public Result findRenshen(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("types","人身意外险");
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/yiliao")
    public Result findYiliao(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("types","医疗养老险");
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/car")
    public Result findCar(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("types","机动车辆险");
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/zeren")
    public Result findZeren(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("types","工伤责任险");
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/caichan")
    public Result findCaichan(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        QueryWrapper<Insurance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("types","财产保障险");
        return Result.success(insuranceService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}

