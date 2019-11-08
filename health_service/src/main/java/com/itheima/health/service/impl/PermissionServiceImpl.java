package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    // 新增权限
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);

    }
    // 分页查询 权限
    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Permission> page = permissionDao.find(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    // 编辑权限返显
    @Override
    public Permission findOne(Integer id) {
        return permissionDao.findOne(id);
    }

    // 编辑权限
    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    // 删除权限
    @Override
    public void delete(Integer id) throws RuntimeException {
        //查询权限是否和角色关联
        if (permissionDao.queryCountById(id) > 0) {
            throw new RuntimeException("该权限与角色存在关联，不能删除");
        }
        // 最终删除操作
        permissionDao.delete(id);
    }

    // 新增、编辑角色时查询所有权限
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
