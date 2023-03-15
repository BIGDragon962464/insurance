package com.czl.insurance.service;

import com.czl.insurance.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czl.insurance.entity.dto.UserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-14
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
