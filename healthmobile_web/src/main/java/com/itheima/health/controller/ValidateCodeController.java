package com.itheima.health.controller;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    // 微信预约
    @RequestMapping("/send4Order")
    public Result validateCode(String telephone){
        try {

            String code = ValidateCodeUtils.generateValidateCode4String(6);

            System.out.println("预约验证码为" + code);

            //存进Redis 并设置有效时间
            //Redis setex(String key, int seconds, String value)
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,300,code);

//          SMSUtils.sendShortMessage("SMS_176523024",telephone,code);
//            SMSUtils.sendShortMessage("SMS_176523024","15625517091","111111");

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    // 用户手机登录
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){

        try {
            // 生成验证码
            String code = ValidateCodeUtils.generateValidateCode4String(4);

            System.out.println("手机登录验证码为" + code);

            // 存进redie（有效5分钟）
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,300,code);

            // SMS工具类发送短信
            //SMSUtils.sendShortMessage("SMS_176523024",telephone,code);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
