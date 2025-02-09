-- 创建用户表
CREATE TABLE userinfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 创建库存表
CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gbgf VARCHAR(255),
    manager_name VARCHAR(255),
    legal_entity VARCHAR(255),
    owner_id VARCHAR(255),
    name VARCHAR(255),
    empl_class VARCHAR(255),
    brand VARCHAR(255),
    model VARCHAR(255),
    imei_meid VARCHAR(255),
    device_type VARCHAR(255),
    asset_id VARCHAR(255),
    inventory_check VARCHAR(255),
    remark VARCHAR(255),
    confirm VARCHAR(255) DEFAULT 'false',
);

-- 创建用户 - 库存关联表
CREATE TABLE user_inventory (
    user_id INT,
    inventory_id INT,
    PRIMARY KEY (user_id, inventory_id),
    FOREIGN KEY (user_id) REFERENCES userinfo(id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(id)
);


INSERT INTO userinfo (username, password) VALUES ('44040850', '44040850');

INSERT INTO inventory (gbgf,manager_name, legal_entity, owner_id, name, empl_class, brand, model, imei_meid, device_type, asset_id, inventory_check, remark, confirm)
VALUES ('CTO', 'John Doe', 'ABC Company', '12345', 'Sample Device', 'Full - time', 'Brand X', 'Model Y', 'IMEI123456', 'Laptop', 'ASSET001', 'Checked', 'No issues', 'false');

INSERT INTO user_inventory (user_id, inventory_id) VALUES (1, 1);