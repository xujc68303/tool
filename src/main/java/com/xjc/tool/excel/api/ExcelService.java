package com.xjc.tool.excel.api;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xjc.tool.excel.object.ExcelUploadModel;
import com.xjc.tool.excel.service.ExcelReadListener;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Version 1.0 @ClassName ExcelService
 * @Author jiachenXu
 * @Date 2020/3/6
 * @Description easyExcel解析工具
 */
public interface ExcelService {

    /**
     * 文件导出为Excel
     *
     * @param data          data
     * @param clazz         映射模型
     * @param excelTypeEnum 导出格式
     * @param response      response
     * @param fileName      文件名称
     */
    @Deprecated
    void export(List<Object> data, Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum,
                HttpServletResponse response, String fileName);

    /**
     * 文件导出为Excel
     *
     * @param data          data
     * @param clazz         映射模型
     * @param excelTypeEnum 导出格式
     * @param response      response
     * @param fileName      文件名称
     * @param sheetName     模板名称
     */
    void export(Map<Object, Object> data, Class<?> clazz, ExcelTypeEnum excelTypeEnum,
                HttpServletResponse response, String fileName, String sheetName);

    /**
     * 解析Excel上传
     *
     * @param file     文件
     * @param clazz    映射模型
     * @param dbHandle 数据操作类
     * @return 文件
     */
    @Deprecated
    List<? extends BaseRowModel> upload(MultipartFile file, Class<? extends BaseRowModel> clazz, Class<?> dbHandle)
            throws IOException;

    ExcelReadListener<ExcelUploadModel> uploadNew(MultipartFile file, Class<?> clazz, Class<?> db) throws IOException;
}
