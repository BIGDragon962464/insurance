package com.czl.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-13
 */
@Data
@Setter
@Getter
@TableName("bysj_orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;
      private Integer userId;

      /**
     * 订单编号
     */
      private String no;

      /**
     * 名称
     */
      private String name;
      private String username;

      /**
     * 订单时间
     */
      private String time;

      /**
     * 支付状态
     */
      private String state;

      /**
       * 交易时间
       */
      private String paymentTime;

      /**
       * 退款时间
       */
      private String returnTime;

      /**
       * 支付宝订单流水号
       */
      private String alipayNo;

      /**
     * 订单总价
     */
      private BigDecimal total;

      /**
       * 订单总价
       */
      private String img;
    

}
