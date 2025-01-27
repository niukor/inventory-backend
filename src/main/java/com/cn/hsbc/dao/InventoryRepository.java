package com.cn.hsbc.dao;

import com.cn.hsbc.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByOwnerId(String ownerId);

}