package com.czl.insurance.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.czl.insurance.service.ITypesService;
import com.czl.insurance.entity.Types;

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
@RequestMapping("/types")
public class TypesController {

    @Resource
    private ITypesService typesService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Types types){
        typesService.saveOrUpdate(types);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        typesService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        typesService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(typesService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(typesService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Types> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(typesService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}

