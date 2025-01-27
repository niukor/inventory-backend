package com.cn.hsbc.dao;

import com.cn.hsbc.entity.UserInventory;
import com.cn.hsbc.entity.UserInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, UserInventoryId> {
}