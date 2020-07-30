package com.fh.shop.admin.util;

import com.fh.shop.admin.po.product.Product;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelUtils {
    public static XSSFWorkbook titleRow(List dataList, String[] props, String[] titles, String name) {
        //创建workbook对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet = workbook.createSheet(name);

        getMaxTitle(sheet, titles);

        bodyRow(dataList, workbook, sheet, props, name);

        return workbook;
    }

    private static void getMaxTitle(XSSFSheet sheet, String[] titles) {

        XSSFRow titleRow = sheet.createRow(13);

        for (int i = 0; i < titles.length; i++) {
            titleRow.createCell(i + 3).setCellValue(titles[i]);
        }
    }

    private static void bodyRow(List dataList, XSSFWorkbook workbook, XSSFSheet sheet, String[] props, String name) {
        XSSFCellStyle priceStyle = workbook.createCellStyle();
        priceStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        //年月日格式
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        dateStyle.setDataFormat(dataFormat.getFormat("yyyy-mm-dd"));

        //时分秒
        XSSFCellStyle dateTimeStyle = workbook.createCellStyle();
        dateTimeStyle.setDataFormat(dataFormat.getFormat("yyyy-mm-dd hh:mm:ss"));

        getBodyStyle(workbook, sheet, props, name);

        try {
            //创建内容
            for (int i = 0; i < dataList.size(); i++) {
                XSSFRow bodyRow = sheet.createRow(i + 14);
                Object obj = dataList.get(i);
                Class<?> objClass = obj.getClass();
                for (int j = 0; j < props.length; j++) {
                    Field declaredField = objClass.getDeclaredField(props[j]);
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(obj);
                    Class<?> type = declaredField.getType();
                    if (type == java.lang.String.class) {
                        bodyRow.createCell(j + 3).setCellValue((String) value);
                    }
                    if (type == java.math.BigDecimal.class) {
                        XSSFCell numCell = bodyRow.createCell(j + 3);
                        numCell.setCellValue(new BigDecimal(String.valueOf(value)).doubleValue());
                        numCell.setCellStyle(priceStyle);
                    }
                    if (type == java.util.Date.class) {
                        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (value != null) {
                            XSSFCell dateCell = bodyRow.createCell(j + 3);
                            dateCell.setCellValue((Date) value);
                            dateCell.setCellStyle(dateStyle);
                        }
                    }
                    if (type == java.lang.Long.class) {
                        bodyRow.createCell(j + 3).setCellValue((Long) value);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void getBodyStyle(XSSFWorkbook workbook, XSSFSheet sheet, String[] props, String name) {
        XSSFRow titleRow1 = sheet.createRow(11);
        XSSFCell titleCell = titleRow1.createCell(3);
        titleCell.setCellValue(name);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(11, 12, 3, props.length + 2);
        sheet.addMergedRegion(cellRangeAddress);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 28);
        font.setColor(HSSFColor.PINK.index);
        titleStyle.setFont(font);
        titleStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCell.setCellStyle(titleStyle);
    }


}
