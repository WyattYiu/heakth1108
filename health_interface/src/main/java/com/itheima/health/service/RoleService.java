package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    // 新增角色
    void add(Role role, List<Integer> permissionList, List<Integer> menuList);

    // 查询条件分页
    PageResult find(QueryPageBean queryPageBean);

    // 编辑回显表单
    Role findOne(Integer id);

    // 编辑回显复选框
    Map<String,List<Integer>> findChecked(Integer id);

    // 编辑保存
    void edit(Role role, List<Integer> permissionList, List<Integer> menuList);

    // 删除角色
    void delete(Integer id);

    // 新增、编辑用户时查询所有角色信息
    List<Role> findAll();


}
