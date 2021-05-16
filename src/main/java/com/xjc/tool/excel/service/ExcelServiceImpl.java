package com.xjc.tool.excel.service;

import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.xjc.tool.date.DateUtil;
import com.xjc.tool.excel.api.ExcelService;
import com.xjc.tool.excel.object.ExcelUploadModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.xjc.tool.date.DateUtil.formatOfLocalDate;


/**
 * @Version 1.0
 * @ClassName ExcelServiceImpl
 * @Author jiachenXu
 * @Date 2020/3/6
 * @Description easyExcel工具
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    private static final String UTF8 = "utf-8";

    @Override
    public void export(List<Object> data, Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum,
                       HttpServletResponse response, String fileName) {

        try (ServletOutputStream outputStream = response.getOutputStream()){
            String fileType = excelTypeEnum.getValue();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLUtil.encode(fileName) + getFileName() + fileType);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(UTF8);
            ExcelWriter excelWriter = new ExcelWriter(outputStream, excelTypeEnum);
            excelWriter.write(data, new Sheet(1, 0, clazz));
            excelWriter.finish();
            outputStream.flush();
            log.info("Excel导出完成");
        }catch (IOException e){
            log.error("excel old export error", e);
        }
    }

    @Override
    public void export(Map<Object, Object> data, Class<?> clazz, ExcelTypeEnum excelTypeEnum,
                       HttpServletResponse response, String fileName, String sheetName) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileType = excelTypeEnum.getValue();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLUtil.encode(fileName) + getFileName() + fileType);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(UTF8);
            EasyExcel.write(outputStream)
                    .head(buildHead(data.keySet()))
                    .sheet(sheetName)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(buildData((List<Object>) data.values()));
        } catch (IOException e) {
            log.error("excel export error", e);
        }
    }

    @Override
    public List<? extends BaseRowModel> upload(MultipartFile file, Class<? extends BaseRowModel> clazz, Class<?> dbHandle) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReadListener<ExcelUploadModel> excelReadListener = new ExcelReadListener<>();
        ExcelReader reader = new ExcelReader(inputStream, null, excelReadListener);
        reader.read(new Sheet(1, 1, clazz));
        log.info("Excel解析完成");
        return excelReadListener.getData();
    }

    @Override
    public ExcelReadListener<ExcelUploadModel> uploadNew(MultipartFile file, Class<?> clazz, Class<?> db) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!type.equals("xlsx") || !type.equals("xls")) {
            throw new IllegalArgumentException("file type error");
        }
        ExcelReadListener<ExcelUploadModel> excelReadListener = null;
        try {
            excelReadListener = new ExcelReadListener<>();
            EasyExcel.read(inputStream, excelReadListener).sheet(0).doRead();
        } finally {
            inputStream.close();
        }
        return null;
    }

    /**
     * 根据当前时间命名文件名称
     *
     * @return 文件名称
     * @throws
     */
    private static String getFileName() {
        return formatOfLocalDate(DateUtil.YYYY_MM_DD);
    }

    private List<List<String>> buildHead(Set<Object> keys) {
        List<List<String>> result = new LinkedList<>();
        keys.forEach(k -> {
            List<String> headers = new LinkedList<>();
            headers.add((String) k);
            result.add(headers);
        });
        return result;
    }

    private List<String> buildData(List<Object> data) {
        List<String> datas = new LinkedList<>();
        data.forEach(d -> {
            datas.add((String) d);
        });
        return datas;
    }

}
