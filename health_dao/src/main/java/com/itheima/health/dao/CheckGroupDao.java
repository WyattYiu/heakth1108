package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void setCheckItem_Checkgroup(HashMap<String, Integer> map);

    Page<CheckItem> find(String queryString);

    CheckGroup findOne(Integer checkGroupId);

    List<Integer> findChecked(Integer checkGroupId);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    Integer queryCountById(Integer id);

    void delete(Integer id);

    List<CheckGroup> findAll();

//    List<CheckGroup> QueryCheckGroupListBySetmealId(Integer id);
}
