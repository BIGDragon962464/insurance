package com.czl.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */
@Data
@Getter
@Setter
@TableName("bysj_insurance")
public class Insurance implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 保险名称
     */
      private String name;

      /**
     * 保险价格
     */
      private Integer price;

      /**
     * 图片
     */
      private String img;

      /**
     * 保险种类
     */
      private String types;

      private String description;



}
