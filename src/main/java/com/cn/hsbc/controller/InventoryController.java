package com.cn.hsbc.controller;

import com.cn.hsbc.dao.InventoryRepository;
import com.cn.hsbc.entity.Inventory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
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

    // 导出库存信息到 Excel
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportInventoriesToExcel() throws IOException {
        // 获取所有库存信息
        List<Inventory> inventories = inventoryRepository.findAll();
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventory");
        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "GBGF", "Manager Name", "Legal Entity", "Owner ID", "Name", "Empl Class","Brand","Model", "IMEI/MEID",
                "Device Type", "Asset ID",  "Inventory Check", "Remark", "Confirm"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        // 填充数据
        int rowNum = 1;
        for (Inventory inventory : inventories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(inventory.getId());
            row.createCell(1).setCellValue(inventory.getGbgf());
            row.createCell(2).setCellValue(inventory.getManagerName());
            row.createCell(3).setCellValue(inventory.getLegalEntity());
            row.createCell(4).setCellValue(inventory.getOwnerId());
            row.createCell(5).setCellValue(inventory.getName());
            row.createCell(6).setCellValue(inventory.getEmplClass());
            row.createCell(7).setCellValue(inventory.getBrand());
            row.createCell(8).setCellValue(inventory.getModel());
            row.createCell(9).setCellValue(inventory.getImeiMeid());
            row.createCell(10).setCellValue(inventory.getDeviceType());
            row.createCell(11).setCellValue(inventory.getAssetId());
            row.createCell(12).setCellValue(inventory.getInventoryCheck());
            row.createCell(13).setCellValue(inventory.getRemark());
            row.createCell(14).setCellValue(inventory.getConfirm());
        }
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // 将工作簿写入字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        // 设置响应头
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentDispositionFormData("attachment", "inventories.xlsx");
        return new ResponseEntity<>(outputStream.toByteArray(), header, HttpStatus.OK);
    }
}