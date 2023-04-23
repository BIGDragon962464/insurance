package com.czl.insurance.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Constants;
import com.czl.insurance.common.Result;
import com.czl.insurance.common.RoleEnum;
import com.czl.insurance.entity.Insurance;
import com.czl.insurance.entity.User;
import com.czl.insurance.exception.ServiceException;
import com.czl.insurance.mapper.DictMapper;
import com.czl.insurance.mapper.OrdersMapper;
import com.czl.insurance.service.IInsuranceService;
import com.czl.insurance.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.czl.insurance.service.IOrdersService;
import com.czl.insurance.entity.Orders;

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
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private IOrdersService ordersService;


    @Resource
    private IInsuranceService insuranceService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Orders orders){
        ordersService.saveOrUpdate(orders);
        return Result.success();
    }

    @PostMapping("/{insuranceId}")
    public Result createOrder(@PathVariable Integer insuranceId){
        Insurance insurance = insuranceService.getById(insuranceId);
        if (insurance == null){
            throw  new ServiceException("-1","未找到保险");
        }
        User currentUser = TokenUtils.getCurrentUser();
        Integer userId = currentUser.getId();
        String username = currentUser.getNickname();

        Orders orders = new Orders();
        orders.setName(insurance.getName());
        orders.setImg(insurance.getImg());
        orders.setNo(DateUtil.format(new Date(),"yyyyMMdd") + System.currentTimeMillis());
        orders.setTime(DateUtil.now());
        orders.setUserId(userId);
        orders.setUsername(username);
        orders.setTotal(BigDecimal.valueOf(insurance.getPrice()));
        ordersService.save(orders);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ordersService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        ordersService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(ordersService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(ordersService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        User currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.ROLE_USER.toString().equals(currentUser.getRole())) {  // 角色是普通用户
            queryWrapper.eq("username", currentUser.getNickname());
        }
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(ordersService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


    @GetMapping("/getOrderInsurance")
    public Result getOrderInsurance(@RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String state){
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.eq("state","已支付");
            queryWrapper.eq("username", username);
        }
        return Result.success(ordersMapper.selectList(queryWrapper));
    }
}

