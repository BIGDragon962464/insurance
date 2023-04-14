package com.czl.insurance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czl.insurance.entity.dto.UserDTO;
import com.czl.insurance.entity.dto.UserPasswordDTO;

import javax.mail.MessagingException;

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

    void updatePassword(UserPasswordDTO userPasswordDTO);

    UserDTO loginEmail(UserDTO userDTO);

    void sendEmailCode(String email, Integer type) throws MessagingException;
}
