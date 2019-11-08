package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {

    // 根据登录用户名获取用户对象
    User findUserByUsername(String username);

    // 新增用户
    void add(User user, List<Integer> roleList);

    // 查询条件分页
    PageResult find(QueryPageBean queryPageBean);

    // 编辑回显表单
    User findOne(Integer id);

    // 编辑回显复选框
    List<Integer> findChecked(Integer id);

    // 编辑保存
    void edit(User user, List<Integer> roleList);

    // 删除用户
    void delete(Integer id);
}
