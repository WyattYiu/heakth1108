package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;


    // 新增权限 [permission.html (add)]
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    // 分页查询 权限 [permission.html (find)]
    @RequestMapping("/find")
    public PageResult find(@RequestBody QueryPageBean queryPageBean) {
        return permissionService.find(queryPageBean);
    }

    // 编辑权限返显 [permission.html (findOne)]
    @RequestMapping("/findOne")
    public Result findOne(Integer id) {
        Permission one = permissionService.findOne(id);
        if (one != null) {
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, one);
        } else {
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    // 编辑权限 [permission.html (edit)]
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionService.edit(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }


    // 删除权限 [permission.html (delete)]
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            permissionService.delete(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }


//     新增、编辑角色时查询所有权限 [role.html (findAll)]
    @RequestMapping("/findAll")
    public Result findAll() {
       List<Permission> permissionList = permissionService.findAll();
        if (permissionList != null && permissionList.size()>0) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,permissionList);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}

