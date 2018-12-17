package leiren.haozhaojob.common.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leiren.haozhaojob.common.annotation.ExcelField;
import leiren.haozhaojob.common.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel工具类
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.13
 * @version 1.0
 */
public class ExcelUtils<T> {
    /**
     * 获取带注解的字段：file到excel
     * @return
     */
    public Map<String, String> getFieldToExcelMap(Class<T> entityClass) {
        Map<String, String> headerMap = new HashMap<>();
        // 获取带注解的字段
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                field.setAccessible(true);
                headerMap.put(field.getName(), excelField.columnName());
            }
        }

        return headerMap;
    }

    /**
     * 获取带注解的字段：file到excel 并且排序
     * @return
     */
    public Map<String, String> getSortFieldToExcelMap(Class<T> entityClass) {
        List<Field> fieldList = new ArrayList<>();
        // 获取带注解的字段
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                fieldList.add(field);
            }
        }

        int length = fieldList.size();
        // 排序
        for (int i = 0; i < length - 1; i++) {
            for (int j = 1; j < length; j++) {
                Field fieldi = fieldList.get(i);
                ExcelField excelFieldi = fieldi.getAnnotation(ExcelField.class);
                fieldi.setAccessible(true);
                int sorti = excelFieldi.sort();

                Field fieldj = fieldList.get(j);
                ExcelField excelFieldj = fieldj.getAnnotation(ExcelField.class);
                fieldj.setAccessible(true);
                int sortj = excelFieldj.sort();

                if(sorti > sortj) {
                    fieldList.set(i, fieldj);
                    fieldList.set(j, fieldi);
                }
            }
        }

        Map<String, String> headerMap = new HashMap<>();
        for(Field field : fieldList) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                field.setAccessible(true);
                headerMap.put(field.getName(), excelField.columnName());
            }
        }

        return headerMap;
    }

    /**
     * 获取带注解的字段：excel到file
     * @return
     */
    public Map<String, String> getExcelToFielMap(Class<T> entityClass) {
        Map<String, String> headerMap = new HashMap<>();
        // 获取带注解的字段
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                field.setAccessible(true);
                headerMap.put(excelField.columnName(), field.getName());
            }
        }

        return headerMap;
    }

    /**
     * 数据库导出到Excel表格
     *
     * @param headerMap Bean字段与Excel题头对应关系map(必须参数--Map(字段名,表头))
     * @param columnWidth 导出Excel的列宽(非必需)
     * @param list 数据beanList(必须参数)
     * @param sheetName Excel页名(必须参数)
     */
    public HSSFWorkbook exportToExcel(Map<String, String> headerMap, int[] columnWidth, List<T> list,
                              String sheetName) {
        return expExcel(headerMap, columnWidth, list, sheetName);
    }

    /**
     * 从Excel表格导入到数据库
     *
     * @param inputStream 输入流
     * @param sheetName Excel页名(必须参数)
     * @param entityClass Bean.class(必须参数)
     * @param headerMap (必须参数)表头与字段对应关系(Map(表头，字段名))
     * @return list
     */
    public List<T> importFromExcel(InputStream inputStream, String sheetName, Class<T> entityClass,
                                   Map<String, String> headerMap) {
        return impExcel(inputStream, sheetName, entityClass, headerMap);
    }

    private static <T> HSSFWorkbook expExcel(Map<String, String> headerMap, int[] columnWidth, List<T> list,
                                     String sheetName) {
        // 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格页
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style1.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());// 绿色背景
        // 设置Excel中的边框(表头的边框)
        style1.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style1.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style1.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style1.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style1.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style1.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style1.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style1.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style1.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
        style1.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font1 = workbook.createFont();// 生成一个字体样式
        font1.setFontHeightInPoints((short) 20); // 字体高度(大小)
        font1.setFontName("楷体"); // 字体
        font1.setColor(HSSFColorPredefined.BLACK.getIndex());
        font1.setBold(true);// 加粗
        style1.setFont(font1);// 把字体应用到当前样式

        // 生成一个表格样式(列表样式---非表头)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());// 蓝色背景
        // 设置Excel中的边框(表头的边框)
        style2.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style2.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style2.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style2.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style2.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style2.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style2.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style2.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style2.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
        style2.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font2 = workbook.createFont();// 生成一个字体样式
        font2.setFontHeightInPoints((short) 14); // 字体高度(大小)
        font2.setFontName("宋体"); // 字体
        font2.setColor(HSSFColorPredefined.BLACK.getIndex());
        font2.setBold(true);// 加粗
        style2.setFont(font2);// 把字体应用到当前样式
        try {
            fillSheet(sheet, style1, style2, list, headerMap);
            // 设置列宽
            if (columnWidth != null && columnWidth.length > 0) {
                for (int i = 0; i < columnWidth.length; i++) {
                    sheet.setColumnWidth(i, columnWidth[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workbook;
    }

    private static <T> List<T> impExcel(InputStream inputStream, String sheetName, Class<T> entityClass,
                                        Map<String, String> headerMap) {
        // 定义要返回的list
        List<T> resultList = new ArrayList<>();
        try {
            // 根据Excel数据源创建WorkBook
            Workbook workbook = Workbook.getWorkbook(inputStream);
            // 获取工作表
            Sheet sheet = workbook.getSheet(sheetName);
            // 获取工作表的有效行数
            int realRows = 0;
            for (int i = 0; i < sheet.getRows(); i++) {
                int nullCols = 0;
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell currentCell = sheet.getCell(j, i);
                    if (currentCell == null || "".equals(currentCell.getContents())) {
                        nullCols++;
                    }
                }

                if (nullCols == sheet.getColumns()) {
                    break;
                } else {
                    realRows++;
                }
            }
            // 如果Excel中没有数据则提示错误
            if (realRows <= 1) {
                throw new ExcelException("Excel文件中没有任何数据");
            }

            Cell[] firstRow = sheet.getRow(0);
            String[] excelFieldNames = new String[firstRow.length];
            // 获取Excel中的列名
            for (int i = 0; i < firstRow.length; i++) {
                excelFieldNames[i] = firstRow[i].getContents().trim();
            }

            // 判断需要的字段在Excel中是否都存在
            boolean isExist = true;
            List<String> excelFieldList = Arrays.asList(excelFieldNames);
            for (String cnName : headerMap.keySet()) {
                if (!excelFieldList.contains(cnName)) {
                    isExist = false;
                    break;
                }
            }
            // 如果有列名不存在，则抛出异常，提示错误
            if (!isExist) {
                throw new ExcelException("Excel中缺少必要的字段，或字段名称有误");
            }

            // 将列名和列号放入Map中,这样通过列名就可以拿到列号
            LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < excelFieldNames.length; i++) {
                colMap.put(excelFieldNames[i], firstRow[i].getColumn());
            }
            // 将sheet转换为list
            for (int i = 1; i < realRows; i++) {
                // 新建要转换的对象
                T entity = entityClass.newInstance();
                // 给对象中的字段赋值
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    // 获取中文字段名
                    String cnNormalName = entry.getKey();
                    // 获取英文字段名
                    String enNormalName = entry.getValue();
                    // 根据中文字段名获取列号
                    int col = colMap.get(cnNormalName);
                    // 获取当前单元格中的内容
                    String content = sheet.getCell(col, i).getContents().trim();
                    // 给对象赋值
                    setFieldValueByName(enNormalName, content, entity);
                }
                resultList.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**************************** 私有辅助方法 ************************/

    /**
     * @param sheet Excel表格
     * @param style1 表头样式
     * @param style2 列表样式
     * @param list 数据源
     * @param fieldMap Bean字段和表头对应关系map
     * @throws Exception
     */
    private static <T> void fillSheet(HSSFSheet sheet, HSSFCellStyle style1, HSSFCellStyle style2, List<T> list,
                                      Map<String, String> fieldMap) throws Exception {
        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];// 英文字段名
        String[] headers = new String[fieldMap.size()];// 中文题头
        // 填充数组
        int count = 0;
        for (Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            headers[count] = entry.getValue();
            count++;
        }
        HSSFRow row = sheet.createRow(0);// 第一行(标题行)
        // 填充第一行列表题头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style1);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            for (int i = 0; i < enFields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldValue = getFieldValueByNameSequence(enFields[i], t);
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if (fieldValue != null) {
                    Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                    Matcher matcher = p.matcher(fieldValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(fieldValue));
                    } else {
                        cell.setCellValue(fieldValue);
                    }
                }
            }
        }
        setColumnAutoSize(sheet);
    }

    /**
     * 根据带路径或不带路径的属性名获取属性值即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence 带路径的属性名或简单属性名
     * @param o 对象
     * @return 属性值
     * @throws Exception
     */
    private static String getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {
        String value;
        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据属性名获取属性对象
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;
    }

    /**
     * 根据字段名获取字段值
     *
     * @param fieldName 字段名
     * @param o 对象
     * @return 字段值
     */
    private static String getFieldValueByName(String fieldName, Object o) throws Exception {
        Object value;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }

        return formatFieldByType(field, value);
    }

    /**
     * 根据字段名获取字段
     *
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();
        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }
        // 如果本类和父类都没有，则返回空
        return null;
    }

    /**
     * 属性值格式化
     *
     * @param field 属性
     * @param value 属性值
     * @return 字段
     */
    private static String formatFieldByType(Field field, Object value) {
        String result = "";
        if(value != null) {
            if(field.getGenericType().toString().equals("class java.util.Date")) {
                result = DateUtils.format((Date)value, DateUtils.DATE_TIME_PATTERN);
            } else {
                result = value.toString();
            }
        }

        return result;
    }

    private static void setColumnAutoSize(HSSFSheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            sheet.autoSizeColumn(i);// 自动设置列宽
        }
    }

    /**
     * 根据字段名给对象的字段赋值
     *
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @param o 对象
     */
    private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {

        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            // 获取字段类型
            Class<?> fieldType = field.getType();
            // 根据字段类型给字段赋值
            if(fieldName.equals("id") && (fieldValue == "" || fieldValue == null)) {
                field.set(o, null);
            } else if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
                }
            } else if (Date.class == fieldType) {
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
    }
}
