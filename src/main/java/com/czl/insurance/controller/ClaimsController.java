package com.czl.insurance.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Result;
import com.czl.insurance.common.RoleEnum;
import com.czl.insurance.entity.User;
import com.czl.insurance.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.czl.insurance.service.IClaimsService;
import com.czl.insurance.entity.Claims;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-13
 */
@RestController
@RequestMapping("/claims")
public class ClaimsController {

    @Resource
    private IClaimsService claimsService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Claims claims){
        if (claims.getId() == null ){
            //新增
            claims.setTime(DateUtil.today());
            claims.setUser(TokenUtils.getCurrentUser().getUsername());
        }
        claimsService.saveOrUpdate(claims);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        claimsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        claimsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(claimsService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(claimsService.getById(id));
    }

    // 分页查询做一下筛选
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Claims> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.ROLE_USER.toString().equals(currentUser.getRole())) {  // 角色是普通用户
            queryWrapper.eq("user", currentUser.getUsername());
        }
        return Result.success(claimsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}

