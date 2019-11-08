package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.HashMap;
import java.util.Map;

public interface OrderDao {


    Order isAlreadyOrder(HashMap<String, String> map);

    void addOrder(Order order);

    Map findById(Integer id);


    Integer findOrderCountBySetmealId(Integer id);

    Integer dayOrderCount(String today);

    Integer dayVisitCount(String today);

    Integer week_monthOrderCount(Map<String, String> map);

    Integer week_monthVisitCount(Map<String, String> map);
}
