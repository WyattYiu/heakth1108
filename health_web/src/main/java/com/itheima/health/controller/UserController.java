package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.TargetAndChild;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    // 获取前登录用户名
        @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }


    // 新增用户
    @RequestMapping("/add")
    public Result add(@RequestBody TargetAndChild targetAndChild) {
        try {
            List<Integer> roleList = targetAndChild.getChild();
            User user = targetAndChild.getUser();
            userService.add(user,roleList);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    // 查询条件分页
    @RequestMapping("/find")
    public PageResult find(@RequestBody QueryPageBean queryPageBean) {
        return userService.find(queryPageBean);
    }

    // 编辑回显表单
    @RequestMapping("/findOne")
    public Result findOne(Integer id){
        User user = userService.findOne(id);
        if (user != null) {
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
        }else {
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }

    // 编辑回显复选框
    @RequestMapping("/findChecked")
    public List<Integer> findChecked(Integer id){
        return userService.findChecked(id);
    }

    // 编辑保存
    @RequestMapping("/edit")
    public Result edit(@RequestBody TargetAndChild targetAndChild) {
        try {
            User user = targetAndChild.getUser();
            List<Integer> roelList = targetAndChild.getChild();
            userService.edit(user,roelList);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

    // 删除用户
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

}
