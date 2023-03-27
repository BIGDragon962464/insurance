package com.czl.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */
@TableName("bysj_types")
public class Types implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 保险种类
     */
      private String types;

      /**
     * 标识
     */
      private String flag;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getTypes() {
        return types;
    }

      public void setTypes(String types) {
          this.types = types;
      }
    
    public String getFlag() {
        return flag;
    }

      public void setFlag(String flag) {
          this.flag = flag;
      }

    @Override
    public String toString() {
        return "Types{" +
              "id=" + id +
                  ", types=" + types +
                  ", flag=" + flag +
              "}";
    }
}
