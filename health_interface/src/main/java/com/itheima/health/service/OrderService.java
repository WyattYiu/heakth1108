package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map;

public interface OrderService {

    Result orderProcess(Map<String,String> map) throws Exception;

    Map findById(Integer id);

    Map<String,Object> findAll();

    Map<String,Object> getBusinessReportData();

}
