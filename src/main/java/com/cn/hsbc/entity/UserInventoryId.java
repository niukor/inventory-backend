package com.cn.hsbc.entity;

import java.io.Serializable;

public class UserInventoryId implements Serializable {
    private Long user;
    private Long inventory;

    // Getters 和 Setters
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }
}