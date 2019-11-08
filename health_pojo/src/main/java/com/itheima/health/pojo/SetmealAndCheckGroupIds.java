package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.List;

public class SetmealAndCheckGroupIds implements Serializable{

    private Setmeal setmeal;
    private List<Integer> checkgroupIds;

    public SetmealAndCheckGroupIds() {
    }

    public SetmealAndCheckGroupIds(Setmeal setmeal, List<Integer> checkgroupIds) {
        this.setmeal = setmeal;
        this.checkgroupIds = checkgroupIds;
    }

    public Setmeal getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(Setmeal setmeal) {
        this.setmeal = setmeal;
    }

    public List<Integer> getCheckgroupIds() {
        return checkgroupIds;
    }

    public void setCheckgroupIds(List<Integer> checkgroupIds) {
        this.checkgroupIds = checkgroupIds;
    }
}

