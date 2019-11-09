package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MenuDao {

    Set<Menu> findMenusByRoleId(Integer roleId);
    List<Menu> findChildrenbyparentMenuId(Integer id);

    /**
     * sj新增
     */

    //添加一条菜单数值
    void addMenu(Menu menu);
    //根据id删除一条菜单数值
    void deleteMenuById(Integer id);
    //删除与menu和role关联表的数据
    void deleteRoleAndMenuById(Integer id);
    //更改menu中的值
    void updateMenuById(Menu menu);
//    //添加关联表的数值
//    void setRoleAndMenu(HashMap<String, Integer> map);

    //根据id查询一条数据
    Menu findMenuById(Integer id);

    //查询各级别的信息
    List<Menu> findAll(Integer level);

    //根据id修改Menu的级别
    void updateLevelById(Map<String, String> map);


    Page<Menu> findPageQuery(String queryString);

    // 根据登录用户名获取父级菜单集合
    List<Menu> getParentMenuListByUsername(String username);

    // 根据上级菜单id查询下级菜单
    List<Menu> getChildrenByParentMenuId(Integer parentMenuId);
}