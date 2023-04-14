package com.czl.insurance.service.impl;

import com.czl.insurance.entity.Orders;
import com.czl.insurance.mapper.OrdersMapper;
import com.czl.insurance.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-13
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
