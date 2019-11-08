package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, List<Integer> integerList) {
        checkGroupDao.add(checkGroup);
        setCheckItem_Checkgroup(checkGroup.getId(),integerList);
    }

    public void setCheckItem_Checkgroup(Integer checkGroupId, List<Integer> integerList){

        HashMap<String, Integer> map = new HashMap();
        map.put("checkgroup_id",checkGroupId);

        if (integerList != null && integerList.size()>0 ) {
            for (Integer integer : integerList) {
                map.put("checkitem_id",integer);
                checkGroupDao.setCheckItem_Checkgroup(map);
            }
        }
    }

    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkGroupDao.find(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findOne(Integer checkGroupId) {
        return checkGroupDao.findOne(checkGroupId);
    }

    @Override
    public List<Integer> findChecked(Integer id) {
        return checkGroupDao.findChecked(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, List<Integer> integerList) {
        checkGroupDao.deleteAssociation(checkGroup.getId());
        checkGroupDao.edit(checkGroup);
        setCheckItem_Checkgroup(checkGroup.getId(),integerList);
    }

    @Override
    public void delete(Integer id) {
        //先查询检查项是否有检查组关联
        if (checkGroupDao.queryCountById(id) > 0) {
            throw new RuntimeException("该检查项与检查组存在关联，不能删除");
        }
        checkGroupDao.delete(id);
    }

    @Override
    public List<CheckGroup> findAll() {
       return checkGroupDao.findAll();
    }
}
