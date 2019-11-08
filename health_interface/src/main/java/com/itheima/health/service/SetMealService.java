package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetMealService {

    void add(Setmeal setMeal, List<Integer> integerList);

    PageResult find(QueryPageBean queryPageBean);

    Setmeal findOne(Integer id);

    List<Integer> findChecked(Integer id);

    void edit(Setmeal setMeal, List<Integer> integerList);

    void delete(Integer id);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}
