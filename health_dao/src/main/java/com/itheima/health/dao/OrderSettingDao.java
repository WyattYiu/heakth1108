package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {

    long findCountByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String s);

    OrderSetting isExistByOrderDate(Date orderDate);

    void upDateReservation(OrderSetting canOrder);
}
