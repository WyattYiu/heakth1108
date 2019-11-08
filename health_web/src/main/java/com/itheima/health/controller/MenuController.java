package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

//    @
//    public Result getMenuByUsername(String username){
//        menuService.getMenuByUsername(username);
//        return Result
//    }


}
