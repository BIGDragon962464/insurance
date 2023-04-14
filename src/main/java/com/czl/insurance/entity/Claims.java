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
 * @since 2023-04-13
 */
@TableName("bysj_claims")
public class Claims implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 保险名
     */
      private String name;

      /**
     * 所属人
     */
      private String user;

      /**
     * 申请时间
     */
      private String time;

      /**
     * 理赔文件
     */
      private String img;

      /**
     * 待审核，审核通过/不通过
     */
      private String state;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getName() {
        return name;
    }

      public void setName(String name) {
          this.name = name;
      }
    
    public String getUser() {
        return user;
    }

      public void setUser(String user) {
          this.user = user;
      }
    
    public String getTime() {
        return time;
    }

      public void setTime(String time) {
          this.time = time;
      }
    
    public String getImg() {
        return img;
    }

      public void setImg(String img) {
          this.img = img;
      }
    
    public String getState() {
        return state;
    }

      public void setState(String state) {
          this.state = state;
      }

    @Override
    public String toString() {
        return "Claims{" +
              "id=" + id +
                  ", name=" + name +
                  ", user=" + user +
                  ", time=" + time +
                  ", img=" + img +
                  ", state=" + state +
              "}";
    }
}
