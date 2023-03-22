package com.czl.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-03-22
 */
@Getter
@Setter
@TableName("bysj_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 路径
     */
      private String path;

      /**
     * 图标
     */
      private String icon;

      /**
     * 描述
     */
      private String description;

      @TableField(exist = false)
      private List<Menu> children;

      private Integer pid;

}
