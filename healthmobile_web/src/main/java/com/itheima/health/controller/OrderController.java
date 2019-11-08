package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> map){

        // 从Redis获取对应的验证码
        String code = jedisPool.getResource().get(map.get("telephone") + RedisMessageConstant.SENDTYPE_ORDER);

        // 验证通过
        if (code !=null && code.equals(map.get("validateCode"))){
            Result result = null;
            try {
                result = orderService.orderProcess(map);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //发送预约成功短信
//            if (result.isFlag()){
//                try {
////                    SMSUtils.sendShortMessage("SMS_176523024",map.get("telephone"), map.get("orderDate"));
//                    SMSUtils.sendShortMessage("SMS_176523024","15625517091", map.get("orderDate"));
//
//                } catch (ClientException e) {
//                    e.printStackTrace();
//                }
//            }
//
            return result;


            // 校验不通过
        }else{
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map = orderService.findById(id);
        if (map != null){
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }
        return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
    }

}
