package com.itheima.health.job;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //获取两个Redis中的差值——即多出的垃圾图片
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //当前时间
        System.out.println(System.currentTimeMillis());
        sdiff.forEach((obj)->{
            System.out.print("冗余图片名称：" +obj);
            //删除服务器中的冗余图片
            QiniuUtils.deleteFileFromQiniu(obj);
            //删除Redis中冗余图片的记录
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,obj);
            System.out.println(obj + "   ✔删除成功");
        });
    }
}
