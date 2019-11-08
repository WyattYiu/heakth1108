package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;


    /*接受参数
验证码校验
    校验不通过
    校验通过
        查询会员是否已存在
        不存在自动注册，跳转登录成功
        已存在，跳转登录成功
设置Cookie 使用Response携带Cookie*/

    @RequestMapping("/check")
    public Result check(HttpServletResponse response,@RequestBody Map<String,String> map){

        // 接收参数
        String validateCode = map.get("validateCode");
        String telephone = map.get("telephone");

        // 验证码校验
        String checkcode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

        //校验通过
        if (validateCode != null && checkcode.equals(validateCode)){
            //查询是否已注册会员
            Member member = memberService.findByTelephone(telephone);

            // 未注册 增加会员
            if (member == null) {
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }

            // 已注册 跳转登录成功
            // 设置Cookie
            Cookie cookie = new Cookie("login_telephone", telephone);
            cookie.setMaxAge(60*60*24*30);//seconds
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }
        return new Result(false, MessageConstant.VALIDATECODE_ERROR);


    }


}
