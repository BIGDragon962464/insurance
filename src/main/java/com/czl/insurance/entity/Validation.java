package com.czl.insurance.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-12
 */
public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      /**
     * 用户邮箱
     */
      private String email;

      /**
     * 验证码
     */
      private String code;

      /**
     * 验证类型
     */
      private Integer type;

      /**
     * 过期时间
     */
      private Date time;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    
    public String getCode() {
        return code;
    }

      public void setCode(String code) {
          this.code = code;
      }
    
    public Integer getType() {
        return type;
    }

      public void setType(Integer type) {
          this.type = type;
      }
    
    public Date getTime() {
        return time;
    }

      public void setTime(Date time) {
          this.time = time;
      }

    @Override
    public String toString() {
        return "Validation{" +
              "id=" + id +
                  ", email=" + email +
                  ", code=" + code +
                  ", type=" + type +
                  ", time=" + time +
              "}";
    }
}
