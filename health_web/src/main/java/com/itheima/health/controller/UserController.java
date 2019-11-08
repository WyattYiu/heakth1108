package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.TargetAndChild;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/10/23 15:58
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    UserService userService;

    @Reference
    MenuService menuService;

    // 获取用户名 [main.html 216 (getUsername)]
    @RequestMapping(value = "/getUsername")
    public Result getUsername(){
        // 获取用户信息（从SpringSecurity）
        try {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }

    }

    // 新增用户 [user.html 366 (add)]
    @RequestMapping("/add")
    public Result add(@RequestBody TargetAndChild targetAndChild) {
        try {
            List<Integer> roleList = targetAndChild.getChild();
            com.itheima.health.pojo.User user = targetAndChild.getUser();
            userService.add(user,roleList);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    // 查询条件分页 [user.html 396 (find)]
    @RequestMapping("/find")
    public PageResult find(@RequestBody QueryPageBean queryPageBean) {
        return userService.find(queryPageBean);
    }

    // 编辑回显表单 [user.html 412 (findOne)]
    @RequestMapping("/findOne")
    public Result findOne(Integer id){
        com.itheima.health.pojo.User user = userService.findOne(id);
        if (user != null) {
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
        }else {
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    // 编辑回显复选框 [user.html 425 (findChecked)]
    @RequestMapping("/findChecked")
    public List<Integer> findChecked(Integer id){
        return userService.findChecked(id);
    }

    // 编辑保存 [user.html 452 (edit)]
    @RequestMapping("/edit")
    public Result edit(@RequestBody TargetAndChild targetAndChild) {
        try {
            com.itheima.health.pojo.User user = targetAndChild.getUser();
            List<Integer> roelList = targetAndChild.getChild();
            userService.edit(user,roelList);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

    // 删除用户 [user.html 483 (delete)]
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }


    //通过用户名获取菜单列表(author:liukeyin time:2019/11/17)
    @RequestMapping(value = "/getMenuListByusername")
    public Result getMenuListByusername() {
        List<Menu> menuList = new ArrayList<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            com.itheima.health.pojo.User user1 = userService.findUserByUsername(username);
            Set<Role> roles = user1.getRoles();

            if (roles != null && roles.size() > 0) {

                LinkedHashSet<Menu> menus =null;
                //遍历得到role
                for (Role role : roles) {
                    // 父菜单
                    menus = role.getMenus();
                    //遍历子菜单
                    for (Menu menu : menus) {
                        // id 下一级
                        System.out.println(menu.getId());
//                        List<Menu> children = menuService.findChildrenbyparentMenuId(menu.getId());
//                        menu.setChildren(children);
                    }
                }

                return new Result(true, MessageConstant.GET_MENU_SUCCESS, menus);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new Result(false, MessageConstant.GET_MENU_FAIL);
    }

    // 修改个人信息回显 [main.html 324 (findMyself)]
    @RequestMapping("/findMyself")
    public Result findMyself(String username){
        com.itheima.health.pojo.User user = userService.findMyself(username);
        if (user != null) {
            return new Result(true, MessageConstant.QUERY_MYSELF_SUCCESS,user);
        }else {
            return new Result(false, MessageConstant.QUERY_MYSELF_FAIL);
        }
    }

    // 更新个人信息 [main.html 354 (updateMyself)]
    @RequestMapping("/updateMyself")
    public Result updateMyself(@RequestBody com.itheima.health.pojo.User user) {
        try {
            userService.updateMyself(user);
            return new Result(true, MessageConstant.UPDATE_MYSELF_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_MYSELF_FAIL);
        }
    }
}
