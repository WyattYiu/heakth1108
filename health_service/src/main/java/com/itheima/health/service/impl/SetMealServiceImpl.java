package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;


@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;

    //新增套餐
    @Override
    public void add(Setmeal setMeal, List<Integer> integerList) {
        setMealDao.add(setMeal);
        if (integerList != null && integerList.size() > 0) {
            addSetMeal_CheckGroupIds(setMeal.getId(),integerList);
        }
        savePic_IntoRedis(setMeal.getImg());
    }

    //新增、编辑外键表
    public void addSetMeal_CheckGroupIds(Integer setMealIds, List<Integer> integerList){

        HashMap<String, Integer> map = new HashMap();
        map.put("setmeal_id",setMealIds);

        if (integerList != null && integerList.size()>0 ) {
            for (Integer integer : integerList) {
                map.put("checkgroup_id",integer);
                setMealDao.addSetMeal_CheckGroupIds(map);
            }
        }
    }

    //新增、编辑后 保存到Redis
    private void savePic_IntoRedis(String pic){
        //提交后的
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    //分页查询
    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setMealDao.find(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    //编辑套餐回显
    @Override
    public Setmeal findOne(Integer checkGroupId) {
        return setMealDao.findOne(checkGroupId);
    }

    //编辑套餐复选框回显
    @Override
    public List<Integer> findChecked(Integer id) {
        return setMealDao.findChecked(id);
    }

    //编辑保存
    @Override
    public void edit(Setmeal setMeal, List<Integer> integerList) {
        //保存编辑信息
        setMealDao.edit(setMeal);
        //删除旧的外键表信息
        setMealDao.deleteAssociation(setMeal.getId());

        //保存新的外键表信息
        if (integerList != null && integerList.size() > 0) {
            addSetMeal_CheckGroupIds(setMeal.getId(),integerList);
        }
        //保存新的图片信息到Redis
        savePic_IntoRedis(setMeal.getImg());
    }

    //删除
    @Override
    public void delete(Integer id) {
        //先查询检查项是否有检查组关联
        if (setMealDao.queryCountById(id) > 0) {
            throw new RuntimeException("该检查项与检查组存在关联，不能删除");
        }
        setMealDao.delete(id);
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setMealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

}
