package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.service.MenuService;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {


}
