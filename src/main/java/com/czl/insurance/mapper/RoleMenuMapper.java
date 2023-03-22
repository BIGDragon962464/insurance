package com.czl.insurance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czl.insurance.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

   @Delete("delete from bysj_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);

   @Select("select menu_id from bysj_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(Integer roleId);
}
