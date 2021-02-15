package com.xjc.tool.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.xjc.tool.excel.object.FieldMeta;

import java.util.*;

/**
 * @Version 1.0
 * @ClassName ExcelReadListener
 * @Author jiachenXu
 * @Date 2020/8/29
 * @Description excel解析数据监控
 */
public class ExcelReadListener<ExcelUploadModel> extends AnalysisEventListener<ExcelUploadModel> {

    private String fileName;

    private String type;

    private List<ExcelUploadModel> listData = new ArrayList<>();

    private Map<Object, FieldMeta> header = new HashMap<>();

    private int MAX_COLUMN_SIZE = 50;

    private int Batch_COUNT = 10;


    public ExcelReadListener() {

    }

    public ExcelReadListener(String fileName, String type) {
        this.fileName = fileName;
        this.type = type;
    }


    void saveData() {
        // 存储DB
        listData.clear();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, Cell> rowCell = context.readRowHolder().getCellMap();
        if (context.readRowHolder().getRowIndex() == 0) {
            if (headMap.values().contains(null)) {
                throw new RuntimeException("表头有null");
            }
            if (rowCell.size() > MAX_COLUMN_SIZE) {
                throw new RuntimeException("列数超过50");
            }

            for(Map.Entry<Integer, Cell> map : rowCell.entrySet()){
                CellData cellData = (CellData) map.getValue();
                FieldMeta fieldMeta = new FieldMeta(cellData.getStringValue(), cellData.getType().name());
                header.put(map.getKey(), fieldMeta);
            }
        }
        validateHeadNameRepeat();
    }

    @Override
    public void invoke(ExcelUploadModel data, AnalysisContext context) {
        // 此方法可用于数据到达某个数量后 分批落库, 防止数据几万条数据OOM
        listData.clear();
        listData.add(data);
        if(listData.size() >= Batch_COUNT){
            if(listData.contains(null)){
                throw new RuntimeException("数据有null");
            }
        }
        saveData();
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 此方法可用于最终操作, 解析Excel后落库


    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        // 检查单元格
        if(CellExtraTypeEnum.MERGE == extra.getType()){
            throw new RuntimeException("第"+ extra.getFirstColumnIndex() + "列，" + "第"+ extra.getLastColumnIndex() + "列"
                    + "第" + extra.getFirstRowIndex() + "行 --> 第" + extra.getFirstRowIndex() + "行， 存在合并单元格");
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExcelUploadModel> getData() {
        return listData;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 检查表头名称不重复
     */
    private void validateHeadNameRepeat(){
        Set<Object> fidldNameSet = new HashSet<>();
        for(FieldMeta fieldMeta : header.values()){
            if(fidldNameSet.contains(fieldMeta.getComment())){
                throw new RuntimeException("表头名称" + fieldMeta.getComment() + "不可以重复");
            } else {
                fidldNameSet.add(fieldMeta.getComment());
            }
        }
    }


}
