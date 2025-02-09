package com.cn.hsbc.controller;

import com.cn.hsbc.dao.InventoryRepository;
import com.cn.hsbc.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventories")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;

    // 获取所有库存信息
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    // 根据 ID 获取库存信息
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 创建库存信息
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    // 更新库存信息
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            // 更新库存信息的各个字段
            inventory.setManagerName(inventoryDetails.getManagerName());
            inventory.setLegalEntity(inventoryDetails.getLegalEntity());
            inventory.setOwnerId(inventoryDetails.getOwnerId());
            inventory.setEmplClass(inventoryDetails.getEmplClass());
            inventory.setImeiMeid(inventoryDetails.getImeiMeid());
            inventory.setDeviceType(inventoryDetails.getDeviceType());
            inventory.setAssetId(inventoryDetails.getAssetId());
            inventory.setInventoryCheck(inventoryDetails.getInventoryCheck());
            inventory.setName(inventoryDetails.getName());
            inventory.setBrand(inventoryDetails.getBrand());
            inventory.setModel(inventoryDetails.getModel());
            inventory.setRemark(inventoryDetails.getRemark());
            // 其他字段以此类推
            Inventory updatedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 确认库存信息
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Inventory> confirmInventory(@PathVariable Long id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            if ("true".equals(inventory.getConfirm())) {
                // 如果已经确认，直接返回
                return new ResponseEntity<>(inventory, HttpStatus.OK);
            }
            inventory.setConfirm("true");
            Inventory updatedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 根据 Owner ID 获取库存信息
    @GetMapping(params = "ownerId")
    public ResponseEntity<List<Inventory>> getInventoriesByOwnerId(@RequestParam String ownerId) {
        List<Inventory> inventories = inventoryRepository.findByOwnerId(ownerId);
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    // 删除库存信息
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInventory(@PathVariable Long id) {
        try {
            inventoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}