package com.itheima.health.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TargetAndChild implements Serializable {

    private CheckGroup checkGroup;
    private Setmeal setmeal;
    private Role role;
    private User user;
    private List<Integer> child;

}
