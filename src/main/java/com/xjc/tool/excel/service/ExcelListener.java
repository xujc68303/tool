package com.xjc.tool.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author jiachenxu
 * @Date 2021/5/20
 * @Descripetion
 */
@Slf4j
public class ExcelListener<ExcelUploadModel> extends AnalysisEventListener<ExcelUploadModel> {


    private List<ExcelUploadModel> datas;

    @Override
    public void invoke(ExcelUploadModel data, AnalysisContext context) {
        datas.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("{}条数据，开始存储数据库！", datas.size());
    }

    public List<ExcelUploadModel> getData() {
        return datas;
    }
}
