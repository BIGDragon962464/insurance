package com.czl.insurance.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.czl.insurance.common.VaildationEnum;
import com.czl.insurance.entity.Validation;
import com.czl.insurance.mapper.ValidationMapper;
import com.czl.insurance.service.IValidationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-04-12
 */
@Service
public class ValidationServiceImpl extends ServiceImpl<ValidationMapper, Validation> implements IValidationService {

    @Override
    public void saveCode(String email, String code, Integer type, DateTime expireDate) {

        Validation validation = new Validation();
        validation.setEmail(email);
        validation.setCode(code);
        validation.setType(type);
        validation.setTime(expireDate);

        //删除同类型的验证
        UpdateWrapper<Validation> validationUpdateWrapper = new UpdateWrapper<>();
        validationUpdateWrapper.eq("email",email);
        validationUpdateWrapper.eq("type", type);
        remove(validationUpdateWrapper);
        //再插入新的code
        save(validation);
    }
}
