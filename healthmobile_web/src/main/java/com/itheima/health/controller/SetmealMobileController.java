package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetMealService setMealService;
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> setmealList = setMealService.getSetmeal();
        if (setmealList != null && setmealList.size() > 0) {
           return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealList);
        }
        return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
    }


    @RequestMapping("findById")
    public Result findById(Integer id){
        Setmeal setmeal = setMealService.findById(id);
        if (setmeal != null) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
}
