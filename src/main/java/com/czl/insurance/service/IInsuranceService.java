package com.czl.insurance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.Insurance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czl.insurance.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */

public interface IInsuranceService extends IService<Insurance> {

    void setBuyInsurance(Integer userId, Integer insuranceId);
    void deleteBuyInsurance(Integer userId, Integer insuranceId);

    List<Insurance> findInsurance(Integer id);
}
