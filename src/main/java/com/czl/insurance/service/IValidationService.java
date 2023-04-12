package com.czl.insurance.service;

import cn.hutool.core.date.DateTime;
import com.czl.insurance.entity.Validation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-12
 */
public interface IValidationService extends IService<Validation> {

    void saveCode(String email, String code, Integer type, DateTime expireDate);
}
