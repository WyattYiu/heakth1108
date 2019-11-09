package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;

import java.util.HashMap;
import java.util.List;

public interface UserDao {

    // 新增用户
    void add(User user);

    // 插入中间表
    void setRole_User(HashMap<String, Integer> map);

    // 查询条件分页
    Page<User> find(String queryString);

    // 查询角色是否有关联
    Integer queryCountById(Integer id);

    // 编辑回显表单
    User findOne(Integer id);

    // 编辑回显复选框
    List<Integer> findChecked(Integer id);

    // 删除中间表
    void deleteAssociation(Integer id);

    // 保存表单信息
    void edit(User user);

    // 删除角色表
    void delete(Integer id);

    // 修改个人信息回显
    User findMyself(String username);

    // 更新个人信息
    void updateMyself(User user);

    User findUserByUsername(String username);
}
