package com.czl.insurance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.Insurance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czl.insurance.entity.User;

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

    Page<Insurance> findInsurance(Page<Insurance> page , Integer id);
}
