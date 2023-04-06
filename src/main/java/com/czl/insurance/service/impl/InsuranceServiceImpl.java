package com.czl.insurance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.insurance.entity.Insurance;
import com.czl.insurance.entity.User;
import com.czl.insurance.mapper.InsuranceMapper;
import com.czl.insurance.mapper.UserMapper;
import com.czl.insurance.service.IInsuranceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-27
 */
@Service
public class InsuranceServiceImpl extends ServiceImpl<InsuranceMapper, Insurance> implements IInsuranceService {

    @Resource
    private InsuranceMapper insuranceMapper;



    @Transactional
    @Override
    public void setBuyInsurance(Integer userId, Integer insuranceId) {
        insuranceMapper.setBuyInsurance(userId,insuranceId);
    }

    @Transactional
    @Override
    public void deleteBuyInsurance(Integer userId, Integer insuranceId) {
        insuranceMapper.deleteBuyInsurance(userId,insuranceId);
    }

    @Override
    public List<Insurance> findInsurance(Integer id) {
        return insuranceMapper.findInsurance(id);
    }

}
