package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.TargetAndChild;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    // 新增角色
    @RequestMapping("/add")
    public Result add(@RequestBody TargetAndChild targetAndChild) {
        try {
            Role role = targetAndChild.getRole();
            List<Integer> permissionList = targetAndChild.getChild();
            roleService.add(role,permissionList);

            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    // 查询条件分页
    @RequestMapping("/find")
    public PageResult find(@RequestBody QueryPageBean queryPageBean) {
        return roleService.find(queryPageBean);
    }

    // 编辑回显表单
    @RequestMapping("/findOne")
    public Result findOne(Integer id){
        Role role = roleService.findOne(id);
        if (role != null) {
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,role);
        }else {
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    // 编辑回显复选框
    @RequestMapping("/findChecked")
    public List<Integer> findChecked(Integer id){
        return roleService.findChecked(id);
    }

    // 编辑保存
    @RequestMapping("/edit")
    public Result edit(@RequestBody TargetAndChild targetAndChild) {
        try {
            Role role = targetAndChild.getRole();
            List<Integer> permissionList = targetAndChild.getChild();
            roleService.edit(role,permissionList);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }


    // 删除角色
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    // 新增、编辑用户时查询所有角色信息
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Role> roleList = roleService.findAll();
        if (roleList != null && roleList.size()>0) {
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleList);
        }
        return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
    }
}
