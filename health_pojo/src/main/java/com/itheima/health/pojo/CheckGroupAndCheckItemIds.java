package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.List;

public class CheckGroupAndCheckItemIds implements Serializable {

    private CheckGroup checkGroup;
    private List<Integer> checkItemIds;

    public CheckGroupAndCheckItemIds() {
    }

    public CheckGroupAndCheckItemIds(CheckGroup checkGroup, List<Integer> checkItemIds) {
        this.checkGroup = checkGroup;
        this.checkItemIds = checkItemIds;
    }

    public CheckGroup getCheckGroup() {
        return checkGroup;
    }

    public void setCheckGroup(CheckGroup checkGroup) {
        this.checkGroup = checkGroup;
    }

    public List<Integer> getCheckItemIds() {
        return checkItemIds;
    }

    public void setCheckItemIds(List<Integer> checkItemIds) {
        this.checkItemIds = checkItemIds;
    }
}
