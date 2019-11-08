package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SetMealDao setMealDao;


    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {

        Map<String, Object> map = new HashMap<>();

        // 获取今日时间
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        // 获取本周时间
        String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String sunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());

        //获取本月时间
        String first = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        String last = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());


        // 当前时间
        map.put("reportDate",today);
        // 今日新增会员
        map.put("todayNewMember",memberDao.dayNewMember(today));
        // 总会员数
        map.put("totalMember",memberDao.findMemberCount());

        // 本周 Map
        Map<String,String> weekMap = new HashMap<>();
        weekMap.put("begin",monday);
        weekMap.put("end",sunday);

        // 本月 Map
        Map<String,String> monthMap = new HashMap<>();
        monthMap.put("begin",first);
        monthMap.put("end",last);

        // 本周新增会员数
        map.put("thisWeekNewMember",memberDao.weekNewMember(monday));
        // 本月新增会员数
        map.put("thisMonthNewMember",memberDao.monthNewMember(first));

        // 今日预约数
        map.put("todayOrderNumber",orderDao.dayOrderCount(today));
        // 今日到诊数
        map.put("todayVisitsNumber",orderDao.dayVisitCount(today));

        // 本周预约数
        map.put("thisWeekOrderNumber",orderDao.week_monthOrderCount(weekMap));
        // 本周到诊数
        map.put("thisWeekVisitsNumber",orderDao.week_monthVisitCount(weekMap));

        // 本月预约数
        map.put("thisMonthOrderNumber",orderDao.week_monthOrderCount(monthMap));
        // 本月到诊数
        map.put("thisMonthVisitsNumber",orderDao.week_monthVisitCount(weekMap));

        // 热门套餐集合
        map.put("hotSetmeal",setMealDao.findHotSetmeal());

        return map;
    }
}
