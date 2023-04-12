package com.czl.insurance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Constants;
import com.czl.insurance.common.VaildationEnum;
import com.czl.insurance.entity.Menu;
import com.czl.insurance.entity.User;
import com.czl.insurance.entity.Validation;
import com.czl.insurance.entity.dto.UserDTO;
import com.czl.insurance.entity.dto.UserPasswordDTO;
import com.czl.insurance.exception.ServiceException;
import com.czl.insurance.mapper.RoleMapper;
import com.czl.insurance.mapper.RoleMenuMapper;
import com.czl.insurance.mapper.UserMapper;
import com.czl.insurance.service.IMenuService;
import com.czl.insurance.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czl.insurance.service.IValidationService;
import com.czl.insurance.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    private static final Log LOG = Log.get();

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private IValidationService validationService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true);//把one传到userDto中
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();    //ROLE_ADMIN
            //设置用户菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public UserDTO loginEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String code = userDTO.getCode();

        //查询邮箱验证的表，查看有没有发送邮箱code，如果不存在就重新获取
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",email);
        validationQueryWrapper.eq("code",code);
        validationQueryWrapper.ge("time",new Date());//查询数据库没过期的code where >= new date()
        Validation validation = validationService.getOne(validationQueryWrapper);
        if (validation == null){
            throw new ServiceException("-1","验证码过期，请重新获取");
        }

        //如果验证通过就查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email); //根据存的user查询用户信息
        User user = getOne(userQueryWrapper);

        if (user == null){
            throw new ServiceException("-1","未找到用户");
        }

        BeanUtil.copyProperties(user,userDTO,true);
        //设置token
        String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
        userDTO.setToken(token);

        String role = user.getRole();
        //设置用户菜单列表
        List<Menu> roleMenus = getRoleMenus(role);
        userDTO.setMenus(roleMenus);
        return userDTO;
    }

    @Override
    public void sendEmailCode(String email) {
        Date now = new Date();
        //先查询同类型的code
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",email);
        validationQueryWrapper.eq("type",VaildationEnum.LOGIN.getCode());
        validationQueryWrapper.ge("time",now);//查询数据库没过期的code
        Validation validation = validationService.getOne(validationQueryWrapper);
        if (validation != null){
            throw new ServiceException("-1","当前您的验证码依然有效，请不要重复发送");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);  //发送人
        message.setTo(email);
        message.setSentDate(now);
        message.setSubject("【龙保险】登陆邮箱验证");
        String code = RandomUtil.randomNumbers(4);//随机四位验证码
        message.setText("本次登陆验证码为" + code + ",有效期 5 分钟，请妥善保管，切勿泄露！");
        javaMailSender.send(message);

        //发送成功之后把验证码存入数据库
        validationService.saveCode(email,code, VaildationEnum.LOGIN.getCode(), DateUtil.offsetMinute(now,1));
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

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO){
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1 ) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }


    private User getUserInfo(@NotNull UserDTO userDTO){
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

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return roleMenus
     */
    private List<Menu> getRoleMenus(String roleFlag){

        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //当前角色所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        //查出系统所有菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();

        //筛选当前用户角色菜单
        for (Menu menu : menus){
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // remove（）  移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}