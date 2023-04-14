package com.czl.insurance.mapper;

import com.czl.insurance.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-13
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    @Update("update bysj_orders set state = #{state}, payment_time = #{paymentTime}, alipay_no = #{alipayNo} where no = #{no}")
    int updateState(@Param("no") String no, @Param("state")String state, @Param("paymentTime")String gmtPayment,
                     @Param("alipayNo")String alipayTradeNo);

}
