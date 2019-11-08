package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult find(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findOne(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
