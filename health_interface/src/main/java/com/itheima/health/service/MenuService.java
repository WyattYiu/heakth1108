package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/11/7
 */
public interface MenuService {
    List<Menu> findChildrenbyparentMenuId(Integer id);

    /**
     * sj新增
     */

    //根据menu添加一条菜单数值
    void addMenu(Menu menu, Integer level);

    //根据id删除一条菜单数值
    void deleteMenuById(Integer id);

    //更改menu的数值
    void updateMenuById(Menu menu);

    //根据id查询一条权限
    Menu findMenuById(Integer id);

    //查询全部(加上级别)
    List<Menu> findAll(Integer level);


    // 分页条件查询
    PageResult findPageQuery(Integer currentPage, Integer pageSize, String queryString);

    // 根据登录用户名动态显示菜单
    List<Menu> getMenuListByUsername(String username);
}
