package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> find(String querystring);

    void delete(Integer id);

    Integer queryCountById(Integer id);

    CheckItem findOne(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
