package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;


    @Override
    public void add(ArrayList<OrderSetting> list) {
        //如果预约集合不为空
        if (list != null && list.size() > 0) {
            list.forEach((orderSetting)->{
                //根据预约日期 - 查询人数
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0){
                    //如果已存在，更新
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    //不存在就添加
                    orderSettingDao.add(orderSetting);
                }
            });
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        //查询当前月的 预约日期和人数
        List<OrderSetting> orderSettingByMonth = orderSettingDao.getOrderSettingByMonth(date + "-%");

        //用于存放多个map
        ArrayList<Map> mapsList = new ArrayList<>();

        orderSettingByMonth.forEach((obj)->{
            //用于存放 日期 可预约人数 和 已预约人数到map中
            HashMap map = new HashMap<>();
            map.put("date",obj.getOrderDate().getDate());
            map.put("number",obj.getNumber());
            map.put("reservations",obj.getReservations());
            mapsList.add(map);
        });
        return mapsList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            //如果已存在，更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            //不存在就添加
            orderSettingDao.add(orderSetting);
        }
    }
}
