package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckGroupAndCheckItemIds;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;


    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) {
        try {
            List<Integer> integerList = checkGroupAndCheckItemIds.getCheckItemIds();
            CheckGroup checkGroup = checkGroupAndCheckItemIds.getCheckGroup();
            checkGroupService.add(checkGroup,integerList);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/find")
    public PageResult find(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.find(queryPageBean);
    }

    @RequestMapping("/findOne")
    public Result findOne(Integer id){
        CheckGroup checkGroup = checkGroupService.findOne(id);
        if (checkGroup != null) {
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findChecked")
    public List<Integer> findChecked(Integer id){
        return checkGroupService.findChecked(id);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) {
        try {
            List<Integer> integerList = checkGroupAndCheckItemIds.getCheckItemIds();
            CheckGroup checkGroup = checkGroupAndCheckItemIds.getCheckGroup();
            checkGroupService.edit(checkGroup,integerList);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if (checkGroupList != null && checkGroupList.size()>0) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupList);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
