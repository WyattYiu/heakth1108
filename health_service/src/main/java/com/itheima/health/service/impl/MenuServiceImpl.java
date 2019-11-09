package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/11/7
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    //用户
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findChildrenbyparentMenuId(Integer id) {
        return menuDao.findChildrenbyparentMenuId(id);
    }

    /**
     * sj新增
     */
    @Override
    public void addMenu(Menu menu, Integer level) {
        menuDao.addMenu(menu);
        Map<String,String> map = new HashMap<>();
        map.put("level", level.toString());
        map.put("name",menu.getName());
        menuDao.updateLevelById(map);
    }

    //根据id删除一条菜单数值
    @Override
    public void deleteMenuById(Integer id) {
        /**
         * 先删除与该条数据有关的关联表
         * 后删除该条数据
         */
        menuDao.deleteRoleAndMenuById(id);
        menuDao.deleteMenuById(id);
    }

    //更改menu中的数值
    @Override
    public void updateMenuById(Menu menu) {
        /**
         * 先删除与该条数据相关的关联表
         * menuDao.deleteRoleAndMenuById(menu.getId());
         * 后更改menu的数值
         * 再添加关联表的数值
         * setRoleAndMenu(menu,roles);
         */
        menuDao.updateMenuById(menu);
    }

    //根据id查询一条权限
    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    //查询各级别的信息
    @Override
    public List<Menu> findAll(Integer level) {
        return menuDao.findAll(level);
    }

    @Override
    public PageResult findPageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Menu> page = menuDao.findPageQuery(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
