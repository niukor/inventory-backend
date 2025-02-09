package com.cn.hsbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "gbgf")
    private String gbgf;
    @Column(name = "manager_name")
    private String managerName;
    @Column(name = "legal_entity")
    private String legalEntity;
    @Column(name = "owner_id")
    private String ownerId;
    @Column(name = "empl_class")
    private String emplClass;
    @Column(name = "imei_meid")
    private String imeiMeid;
    @Column(name = "device_type")
    private String deviceType;
    @Column(name = "asset_id")
    private String assetId;
    @Column(name = "inventory_check")
    private String inventoryCheck;
    private String name;
    private String brand;
    private String model;
    private String remark;

    @Column(name = "confirm")
    private String confirm;

    // Getters 和 Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGbgf() {
        return gbgf;
    }

    public void setGbgf(String gbgf) {
        this.gbgf = gbgf;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmplClass() {
        return emplClass;
    }

    public void setEmplClass(String emplClass) {
        this.emplClass = emplClass;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImeiMeid() {
        return imeiMeid;
    }

    public void setImeiMeid(String imeiMeid) {
        this.imeiMeid = imeiMeid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getInventoryCheck() {
        return inventoryCheck;
    }

    public void setInventoryCheck(String inventoryCheck) {
        this.inventoryCheck = inventoryCheck;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getConfirm() {
        return confirm;
    }
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}