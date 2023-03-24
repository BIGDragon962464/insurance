package com.czl.insurance.mapper;

import com.czl.insurance.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from bysj_role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
