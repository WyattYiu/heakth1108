package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetMealDao {

    void add(Setmeal setMeal);

    void addSetMeal_CheckGroupIds(HashMap<String, Integer> map);

    Page<Setmeal> find(String queryString);

    Setmeal findOne(Integer checkGroupId);

    List<Integer> findChecked(Integer id);

    void edit(Setmeal setMeal);

    void deleteAssociation(Integer id);

    Integer queryCountById(Integer id);

    void delete(Integer id);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map<String,Object>> findHotSetmeal();
}
