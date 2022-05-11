package com.xjc.tool.image.api;

import java.io.IOException;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
public interface ImageService {

    /**
     * 图片压缩
     *
     * @param source        源文件
     * @param target        目标文件
     * @param scale         压缩大小
     * @param outputQuality 压缩质量
     * @param format        格式
     * @throws IOException
     */
    void compression(String source, String target, double scale, double outputQuality, String format) throws IOException;

    /**
     * 图片略缩图
     *
     * @param source 源文件
     * @param target 目标文件
     * @param width   宽
     * @param height 高
     * @param format 格式
     * @throws IOException
     */
    void scaling(String source, String target, Integer width, Integer height, String format) throws IOException;
}
