package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    void add(CheckGroup checkGroup, List<Integer> integerList);

    PageResult find(QueryPageBean queryPageBean);

    CheckGroup findOne(Integer checkGroupId);

    List<Integer> findChecked(Integer id);

    void edit(CheckGroup checkGroup, List<Integer> integerList);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
