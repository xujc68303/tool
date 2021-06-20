package com.xjc.tool.pdf.api;

import com.xjc.tool.pdf.model.ImageExt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
public interface PdfService {

    /**
     * 合并全部pdf为一张pdf图
     *
     * @param filePathList 待合并的pdf
     * @param targetPath   最终合并结果路径
     * @throws IOException
     */
    void mulFile2One(List<File> filePathList, String targetPath) throws IOException;

    /**
     * 合并多张图片为一张pdf
     *
     * @param imagePathList 图片源
     * @param isPartition   是否分片处理
     * @param partition     分片数量
     * @param extMap        图片信息
     * @param outPath       输出路径
     * @return 合并pdf图片
     * @throws IllegalArgumentException
     */
    List<String> mergeImage2One(List<File> imagePathList, boolean isPartition, int partition,
                                Map<String, ImageExt> extMap, String outPath) throws IllegalArgumentException;
}
