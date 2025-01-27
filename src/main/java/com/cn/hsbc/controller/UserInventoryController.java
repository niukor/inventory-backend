package com.cn.hsbc.controller;

import com.cn.hsbc.dao.UserInventoryRepository;
import com.cn.hsbc.entity.UserInventory;
import com.cn.hsbc.entity.UserInventoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-inventories")
@CrossOrigin(origins = "http://118.31.3.45:3000")
public class UserInventoryController {
    @Autowired
    private UserInventoryRepository userInventoryRepository;

    // 获取所有用户 - 库存关联信息
    @GetMapping
    public ResponseEntity<List<UserInventory>> getAllUserInventories() {
        List<UserInventory> userInventories = userInventoryRepository.findAll();
        return new ResponseEntity<>(userInventories, HttpStatus.OK);
    }

    // 根据复合主键获取用户 - 库存关联信息
    @GetMapping("/{userId}/{inventoryId}")
    public ResponseEntity<UserInventory> getUserInventoryById(@PathVariable Long userId, @PathVariable Long inventoryId) {
        UserInventoryId id = new UserInventoryId();
        id.setUser(userId);
        id.setInventory(inventoryId);
        Optional<UserInventory> userInventory = userInventoryRepository.findById(id);
        return userInventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 创建用户 - 库存关联信息
    @PostMapping
    public ResponseEntity<UserInventory> createUserInventory(@RequestBody UserInventory userInventory) {
        UserInventory savedUserInventory = userInventoryRepository.save(userInventory);
        return new ResponseEntity<>(savedUserInventory, HttpStatus.CREATED);
    }

    // 更新用户 - 库存关联信息
    @PutMapping("/{userId}/{inventoryId}")
    public ResponseEntity<UserInventory> updateUserInventory(@PathVariable Long userId, @PathVariable Long inventoryId,
                                                             @RequestBody UserInventory userInventoryDetails) {
        UserInventoryId id = new UserInventoryId();
        id.setUser(userId);
        id.setInventory(inventoryId);
        Optional<UserInventory> optionalUserInventory = userInventoryRepository.findById(id);
        if (optionalUserInventory.isPresent()) {
            UserInventory userInventory = optionalUserInventory.get();
            userInventory.setUser(userInventoryDetails.getUser());
            userInventory.setInventory(userInventoryDetails.getInventory());
            UserInventory updatedUserInventory = userInventoryRepository.save(userInventory);
            return new ResponseEntity<>(updatedUserInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 删除用户 - 库存关联信息
    @DeleteMapping("/{userId}/{inventoryId}")
    public ResponseEntity<HttpStatus> deleteUserInventory(@PathVariable Long userId, @PathVariable Long inventoryId) {
        UserInventoryId id = new UserInventoryId();
        id.setUser(userId);
        id.setInventory(inventoryId);
        try {
            userInventoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}