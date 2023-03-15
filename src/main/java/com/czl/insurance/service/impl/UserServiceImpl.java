package com.czl.insurance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czl.insurance.common.Constants;
import com.czl.insurance.entity.User;
import com.czl.insurance.entity.dto.UserDTO;
import com.czl.insurance.exception.ServiceException;
import com.czl.insurance.mapper.UserMapper;
import com.czl.insurance.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czl.insurance.utils.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true);//把one传到userDto中
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);//把one传到userDto中
            save(one);//把copy完之后的用户对象存储到数据库中
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        }catch (Exception e ){
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

}