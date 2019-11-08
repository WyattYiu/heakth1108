package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    // 新增角色
    void add(Role role);

    // 插入角色权限中间表
    void setRole_Permission(HashMap<String, Integer> map);

    // 插入角色菜单中间表
    void setRole_Menu(HashMap<String, Integer> map);

    // 查询条件分页
    Page<Role> find(String queryString);

    // 编辑回显表单
    Role findOne(Integer id);

    // 编辑回显权限复选框
    List<Integer> findCheckedPermissions(Integer id);

    // 编辑回显菜单复选框
    List<Integer> findCheckedMenus(Integer id);

    // 删除权限中间表
    void deleteRole_Permission(Integer id);

    // 删除菜单中间表
    void deleteRole_menu(Integer id);

    // 保存表单信息
    void edit(Role role);

    // 查询角色是否有关联
    Integer queryCountById(Integer id);

    // 删除角色表
    void delete(Integer id);

    // 新增、编辑用户时查询所有角色信息
    List<Role> findAll();

    // 查询角色是否有关联 权限表
    Integer queryPermissionCountById(Integer id);

    // 查询角色是否有关联 菜单表
    Integer queryMenuCountById(Integer id);

}
