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

    // 插入中间表
    void setRole_Permission(HashMap<String, Integer> map);

    // 查询条件分页
    Page<Role> find(String queryString);

    // 编辑回显表单
    Role findOne(Integer id);

    // 编辑回显复选框
    List<Integer> findChecked(Integer id);

    // 删除中间表
    void deleteAssociation(Integer id);

    // 保存表单信息
    void edit(Role role);

    // 查询角色是否有关联
    Integer queryCountById(Integer id);

    // 删除角色表
    void delete(Integer id);

    // 新增、编辑用户时查询所有角色信息
    List<Role> findAll();
}

