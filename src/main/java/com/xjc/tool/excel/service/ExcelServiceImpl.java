package com.xjc.tool.excel.service;

import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.xjc.tool.date.DateUtil;
import com.xjc.tool.excel.api.ExcelService;
import com.xjc.tool.excel.object.ExcelUploadModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileType = excelTypeEnum.getValue();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLUtil.encode(fileName) + getFileName() + fileType);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(UTF8);
            ExcelWriter excelWriter = new ExcelWriter(outputStream, excelTypeEnum);
            excelWriter.write(data, new Sheet(1, 0, clazz));
            excelWriter.finish();
            outputStream.flush();
            log.info("Excel导出完成");
        } catch (IOException e) {
            log.error("excel old export error", e);
        }
    }

    @Override
    public void export(Map<Object, Object> data, Class<?> clazz, ExcelTypeEnum excelTypeEnum,
                       HttpServletResponse response, String fileName, String sheetName) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileType = excelTypeEnum.getValue();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLUtil.encode(fileName) + getFileName() + fileType);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(UTF8);
            // head
            WriteCellStyle headWriteCellStyle = getHeadStyle();
            // content
            WriteCellStyle contentWriteCellStyle = getContentStyle();
            EasyExcel.write(outputStream)
                    .head(buildHead(data.keySet()))
                    .sheet(sheetName)
                    .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                    .doWrite(buildData((List<Object>) data.values()));
        } catch (IOException e) {
            log.error("excel export error", e);
        }
    }

    @NotNull
    private WriteCellStyle getContentStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 水平剧中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return contentWriteCellStyle;
    }

    @NotNull
    private WriteCellStyle getHeadStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 16);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        return headWriteCellStyle;
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

    @Override
    public List<?> syncUpLoad(MultipartFile file, Class<?> header) throws IOException {
        return EasyExcel.read(file.getInputStream(), header, null).doReadAllSync();
    }

    @Override
    public Map<String, List<ExcelUploadModel>> asyncUpLoad(MultipartFile file, Class<?> header) throws IOException {
        ExcelListener readListener = new ExcelListener();
        EasyExcel.read(file.getInputStream(), header, readListener).doReadAllSync();
        Map<String, List<ExcelUploadModel>> map = new HashMap<>();
        map.put("res", readListener.getData());
        return map;
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
