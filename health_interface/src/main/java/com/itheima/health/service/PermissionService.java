package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionService {

    // 新增权限
    void add(Permission permission);

    // 分页条件 查询
    PageResult find(QueryPageBean queryPageBean);

    // 编辑权限
    void edit(Permission permission);

    // 编辑时返显
    Permission findOne(Integer id);

    // 删除权限
    void delete(Integer id);


    // 新增、编辑角色时查询所有权限
    List<Permission> findAll();
}
