package com.czl.insurance.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.Insurance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czl.insurance.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */
public interface InsuranceMapper extends BaseMapper<Insurance> {

    void setBuyInsurance(@Param("userId")Integer userId, @Param("insuranceId")Integer insuranceId);
    void deleteBuyInsurance(@Param("userId")Integer userId, @Param("insuranceId")Integer insuranceId);

    List<Insurance> findInsurance(@Param("id")Integer id);

}
