package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuServise;

    //添加菜单的数值
    @RequestMapping("/add")
    public Result addMenu(@RequestBody Menu menu, Integer level) {
        try {
            menuServise.addMenu(menu, level);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    //根据id删除一条菜单数值
    @RequestMapping("/delete")
    public Result deleteMenuById(Integer id) {
        try {
            menuServise.deleteMenuById(id);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
    }

    //更新menu中的数值
    @RequestMapping("/update")
    public Result updateMenuById(@RequestBody Menu menu) {
        try {
            menuServise.updateMenuById(menu);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

    //查询父菜单
    @RequestMapping("/findAll")
    public Result findAll(Integer level) {
        System.out.println(level);
        List<Menu> menu = menuServise.findAll(level);
        if (menu != null && menu.size() > 0) {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
        } else {
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    //根据Id查询一条权限
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Menu menu = menuServise.findMenuById(id);
        if (menu != null) {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
        } else {
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/find")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        // 传递当前页，每页显示的记录数，查询条件
        // 响应PageResult，封装总记录数，结果集
        PageResult pageResult = menuServise.findPageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //根据当前登录的用户名动态展示菜单列表
    @RequestMapping("/getMenuListByUsername")
    public Result getMenuListByUsername() {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Menu> menuList = menuServise.getMenuListByUsername(user.getUsername());
            return new Result(true, MessageConstant.GET_MENU_SUCCESS, menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }
}
