package com.cn.hsbc.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelToSql {

    public static void main(String[] args) {
        String fileName = "inventories.xlsx";
        String tableName = "your_table_name";
        // 自定义列名
        String[] customColumnNames = {"GBGF", "MANAGER_NAME", "LEGAL_ENTITY","OWNER_ID","NAME","EMPL_CLASS","BRAND","MODEL","IMEI_MEID","DEVICE_TYPE","ASSET_ID","INVENTORY_CHECK","REMARK","CONFIRM"};

        try {
            List<String[]> data = readExcelFromResources(fileName);
            List<String> sqlStatements = generateSql(data, tableName, customColumnNames);
            for (String sql : sqlStatements) {
                System.out.println(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readExcelFromResources(String fileName) throws IOException {
        List<String[]> data = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);
        try (InputStream inputStream = resource.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String[] rowData = new String[row.getLastCellNum()];
                for (Cell cell : row) {
                    rowData[cell.getColumnIndex()] = getCellValueAsString(cell);
                }
                data.add(rowData);
            }
        }
        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public static List<String> generateSql(List<String[]> data, String tableName, String[] columnNames) {
        List<String> sqlStatements = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName).append(" (");
            for (int j = 0; j < columnNames.length; j++) {
                sql.append(columnNames[j]);
                if (j < columnNames.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            String[] row = data.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == null) {
                    sql.append("NULL");
                } else {
                    sql.append("'").append(row[j].replace("'", "''")).append("'");
                }
                if (j < row.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(");");
            sqlStatements.add(sql.toString());
        }
        return sqlStatements;
    }
}