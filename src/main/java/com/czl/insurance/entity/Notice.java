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
 * @since 2023-04-15
 */
@TableName("bysj_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 标题
     */
      private String name;

      /**
     * 内容
     */
      private String content;

      /**
     * 发布时间
     */
      private String time;

      /**
     * 发布人
     */
      private String user;

      /**
     * 封面
     */
      private String img;

    
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
    
    public String getContent() {
        return content;
    }

      public void setContent(String content) {
          this.content = content;
      }
    
    public String getTime() {
        return time;
    }

      public void setTime(String time) {
          this.time = time;
      }
    
    public String getUser() {
        return user;
    }

      public void setUser(String user) {
          this.user = user;
      }
    
    public String getImg() {
        return img;
    }

      public void setImg(String img) {
          this.img = img;
      }

    @Override
    public String toString() {
        return "Notice{" +
              "id=" + id +
                  ", name=" + name +
                  ", content=" + content +
                  ", time=" + time +
                  ", user=" + user +
                  ", img=" + img +
              "}";
    }
}
