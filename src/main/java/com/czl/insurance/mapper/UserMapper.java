package com.czl.insurance.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czl.insurance.entity.dto.UserPasswordDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-14
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update bysj_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findPage(Page<User> page, @Param("username") String username,@Param("nickname") String nickname,@Param("address") String address);
}
