package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    // 新增角色
    @Override
    public void add(Role role, List<Integer> permissionList, List<Integer> menuList) {

        // 插入role表
        roleDao.add(role);

        // 插入角色权限中间表
        setRole_Permission(role.getId(),permissionList);

        // 插入角色菜单中间表
        setRole_Menu(role.getId(),menuList);
    }

    // 插入角色权限中间表
    public void setRole_Permission(Integer RoleId, List<Integer> permissionList){
        HashMap<String, Integer> map = new HashMap();
        map.put("role_id",RoleId);
        if (permissionList != null && permissionList.size()>0 ) {
            for (Integer integer : permissionList) {
                map.put("permission_id",integer);
                roleDao.setRole_Permission(map);
            }
        }
    }

    // 插入角色菜单中间表
    public void setRole_Menu(Integer RoleId, List<Integer> menuList){
        HashMap<String, Integer> map = new HashMap();
        map.put("role_id",RoleId);
        if (menuList != null && menuList.size()>0 ) {
            for (Integer integer : menuList) {
                map.put("menu_id",integer);
                roleDao.setRole_Menu(map);
            }
        }
    }


    // 查询条件分页
    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Role> page = roleDao.find(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    // 编辑回显表单
    @Override
    public Role findOne(Integer id) {
        return roleDao.findOne(id);
    }

    // 编辑回显复选框
    @Override
    public Map<String,List<Integer>> findChecked(Integer id) {
        HashMap<String, List<Integer>> map = new HashMap<>();
        map.put("permissionIds",roleDao.findCheckedPermissions(id));
        map.put("menuIds",roleDao.findCheckedMenus(id));
        return map;
    }

    // 编辑保存
    @Override
    public void edit(Role role, List<Integer> permissionList, List<Integer> menuList) {
        // 删除权限中间表
        roleDao.deleteRole_Permission(role.getId());
        // 删除菜单中间表
        roleDao.deleteRole_menu(role.getId());
        // 保存表单信息
        roleDao.edit(role);
        // 插入权限中间表
        setRole_Permission(role.getId(),permissionList);
        // 插入角色菜单中间表
        setRole_Menu(role.getId(),menuList);
    }

    // 删除角色
    @Override
    public void delete(Integer id) {
        //先查询角色是否有角色菜单关联
        if (roleDao.queryPermissionCountById(id) > 0 || roleDao.queryMenuCountById(id) > 0 ) {
            throw new RuntimeException("该角色与角色菜单存在关联，不能删除");
        }
        roleDao.delete(id);
    }

    @Override
    public List<Role> findAll() {
       return roleDao.findAll();
    }
}
