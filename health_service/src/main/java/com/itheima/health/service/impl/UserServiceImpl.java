package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    // 新增用户
    @Override
    public void add(User user, List<Integer> roleList) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pasword = passwordEncoder.encode(user.getPassword());
        user.setPassword(pasword);
        userDao.add(user);
        setRole_User(user.getId(),roleList);
    }

    // 插入中间表
    public void setRole_User(Integer userId, List<Integer> roleList){

        HashMap<String, Integer> map = new HashMap();
        map.put("user_id",userId);

        if (roleList != null && roleList.size()>0 ) {
            for (Integer roleId : roleList) {
                map.put("role_id",roleId);
                userDao.setRole_User(map);
            }
        }
    }

    // 查询条件分页
    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> page = userDao.find(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    // 编辑回显表单
    @Override
    public User findOne(Integer id) {
        return userDao.findOne(id);
    }

    // 编辑回显复选框
    @Override
    public List<Integer> findChecked(Integer id) {
        return userDao.findChecked(id);
    }

    // 编辑保存
    @Override
    public void edit(User user, List<Integer> roleList) {
        // 删除中间表
        userDao.deleteAssociation(user.getId());
        // 保存表单信息
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pasword = passwordEncoder.encode(user.getPassword());
        user.setPassword(pasword);
        userDao.edit(user);
        // 插入中间表
        setRole_User(user.getId(),roleList);
    }

    // 删除用户
    @Override
    public void delete(Integer id) {
        //先查询检查项是否有检查组关联
        if (userDao.queryCountById(id) > 0) {
            throw new RuntimeException("该检查项与检查组存在关联，不能删除");
        }
        userDao.delete(id);
    }
}
