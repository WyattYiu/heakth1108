package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    // 新增权限
    void add(Permission permission);

    // 分页条件 查询
    Page<Permission> find(String queryString);

    // 编辑权限
    void edit(Permission permission);

    // 编辑时返显
    Permission findOne(Integer id);

    // 删除权限
    void delete(Integer id);

    // 查询权限是否和角色关联
    Integer queryCountById(Integer id);

    // 新增、编辑角色时查询所有权限
    List<Permission> findAll();

    // 根据角色Id查询权限
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
