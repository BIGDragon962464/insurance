package com.czl.insurance.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.common.Constants;
import com.czl.insurance.common.Result;
import com.czl.insurance.config.AuthAccess;
import com.czl.insurance.entity.Validation;
import com.czl.insurance.entity.dto.UserDTO;
import com.czl.insurance.entity.dto.UserPasswordDTO;
import com.czl.insurance.exception.ServiceException;
import com.czl.insurance.service.IValidationService;
import com.czl.insurance.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.czl.insurance.service.IUserService;
import com.czl.insurance.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IValidationService validationService;

    //登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }
    @AuthAccess
    @PostMapping("/loginEmail")
    public Result loginEmail(@RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        String code = userDTO.getCode();
        if (StrUtil.isBlank(email) || StrUtil.isBlank(code)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.loginEmail(userDTO);
        return Result.success(dto);
    }

    @Resource
    private IUserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    @AuthAccess
    @PutMapping("/reset")
    public Result reset(@RequestBody UserPasswordDTO userPasswordDTO){
        if (StrUtil.isBlank(userPasswordDTO.getEmail()) || StrUtil.isBlank(userPasswordDTO.getCode())){
            throw new ServiceException("-1","参数错误");
        }
        //查询邮箱验证的表，查看有没有发送邮箱code，如果不存在就重新获取
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",userPasswordDTO.getEmail());
        validationQueryWrapper.eq("code",userPasswordDTO.getCode());
        validationQueryWrapper.ge("time",new Date());//查询数据库没过期的code where >= new date()
        Validation validation = validationService.getOne(validationQueryWrapper);
        if (validation == null){
            throw new ServiceException("-1","验证码过期，请重新获取");
        }

        //如果验证通过就查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", userPasswordDTO.getEmail()); //根据存的user查询用户信息
        User user = userService.getOne(userQueryWrapper);

        //重置密码
        user.setPassword("123");
        userService.updateById(user);
        return Result.success();
    }

    //新增或更新
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")//删除
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @AuthAccess
    @GetMapping("/email/{email}/{type}")
    public Result sendEmailCode(@PathVariable String email, @PathVariable Integer type ) throws MessagingException {
        if (StrUtil.isBlank(email)){
            throw new ServiceException(Constants.CODE_400,"参数错误");
        }
        if (type == null){
            throw new ServiceException(Constants.CODE_400,"参数错误");
        }
        userService.sendEmailCode(email,type );
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }


    @GetMapping("/username/{username}")
    public Result findUser(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }




    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String username,
                               @RequestParam (defaultValue = "")String nickname,
                               @RequestParam (defaultValue = "")String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if (!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if (!"".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");

        //获取当前用户信息
        /*User currentUser = TokenUtils.getCurrentUser();
        System.out.println("获取当前用户信息==============================" + currentUser.getNickname());*/


        return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    /**
     * 导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询出所有的数据
        List<User> list= userService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.addHeaderAlias("trueName", "真实姓名");
        writer.addHeaderAlias("trueId", "身份证号");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setNickname(row.get(3).toString());
            user.setEmail(row.get(4).toString());
            user.setPhone(row.get(5).toString());
            user.setAddress(row.get(6).toString());
            user.setAvatarUrl(row.get(7).toString());
            user.setTrueName(row.get(8).toString());
            user.setTrueId(row.get(9).toString());
            users.add(user);
        }

        userService.saveBatch(users);
        return Result.success(true);
    }


}

