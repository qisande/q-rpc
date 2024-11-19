package com.qisande.example.common.model;

import java.io.Serializable;

/**
 * @author qisan
 * @date 2024-10-11 20:07:08
 * @description: 用户
 */
public class User implements Serializable {
    private static final long serialVersionUID = 7333719815796003098L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
