package com.czl.insurance.entity.dto;


import com.czl.insurance.entity.Menu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接受前端登录请求的参数
 */
@Getter
@Setter
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String code;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private String trueName;
    private String trueId;
    private List<Menu> menus;

}