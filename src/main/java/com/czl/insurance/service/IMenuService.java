package com.czl.insurance.service;

import com.czl.insurance.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 曹子龙
 * @since 2023-03-22
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);

}
