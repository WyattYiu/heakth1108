package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.OrderService;
import com.itheima.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SetMealDao setMealDao;


    @Override
    public Result orderProcess(Map<String,String> map) throws Exception {



        // 查询当天是否可以预约
        OrderSetting canOrder = orderSettingDao.isExistByOrderDate(DateUtils.parseString2Date(map.get("orderDate")));
        // 可以预约
        if (canOrder != null) {
            // 预约人数未满 可预约
            if (canOrder.getNumber()>=canOrder.getReservations()){

                // 查询是否会员
             Member isMember = memberDao.isExistByTelephone((String) map.get("telephone"));

                HashMap<String, String> ordermap = new HashMap<>();
                ordermap.put("orderDate",map.get("orderDate"));
                ordermap.put("setmealId",map.get("setmealId"));
                ordermap.put("orderType",Order.ORDERTYPE_WEIXIN);
                ordermap.put("orderStatus",Order.ORDERSTATUS_NO);

                // 已注册会员
                if (isMember != null) {
                    ordermap.put("memberId",isMember.getId().toString());
                    Order alreadyOrder = orderDao.isAlreadyOrder(ordermap);

                    // 查询是否已经预约
                    // 已经预约
                    if (alreadyOrder != null) {
                        return new Result(false,MessageConstant.HAS_ORDERED);
                    }
                    //未注册会员
                }else {
                    isMember = new Member();
                    isMember.setName(map.get("name"));
                    isMember.setPhoneNumber(map.get("telephone"));
                    isMember.setIdCard(map.get("idCard"));
                    isMember.setSex(map.get("sex"));
                    isMember.setRegTime(new Date());
                    memberDao.add(isMember);
                }
                //更新ordermap 补全【orderType】【orderStatus】
                Order order = new Order(isMember.getId(), DateUtils.parseString2Date(ordermap.get("orderDate")), ordermap.get("orderType"), Order.ORDERSTATUS_NO, Integer.parseInt(ordermap.get("setmealId")));

                //【已注册未预约】和【未注册】一样都是未预约
                //(进行预约)
                orderDao.addOrder(order);

                //ordersetting 的 【reservation】+ 1
                canOrder.setReservations(canOrder.getReservations() + 1);
                orderSettingDao.upDateReservation(canOrder);
                return new Result(true,MessageConstant.ORDER_SUCCESS,order);

            // 预约人数已满 不可预约
            }
            return new Result(false, MessageConstant.ORDER_FULL);
            // 不可预约
        }else{
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
    }

    @Override
    public  Map findById(Integer id) {
       return orderDao.findById(id);

    }

    @Override
    public Map<String,Object> findAll() {
        // 套餐集合
        List<Setmeal> setmealList = setMealDao.getSetmeal();
        //套餐名称，数量集合
        List<Map<String,Object>> list = new ArrayList<>();
        //套餐名称集合
        List<String> setmealName = new ArrayList<>();

        for (Setmeal setmeal : setmealList) {
            HashMap<String, Object> orderMap = new HashMap<>();
            Integer OrderCount = orderDao.findOrderCountBySetmealId(setmeal.getId());
            orderMap.put("name",setmeal.getName());
            orderMap.put("value",OrderCount);
            list.add(orderMap);
            setmealName.add(setmeal.getName());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("setmealName",setmealName);
        map.put("Setmeal",list);

        return map;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        return null;

    }
}
