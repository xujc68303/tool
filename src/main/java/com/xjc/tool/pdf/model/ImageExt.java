package com.xjc.tool.pdf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageExt implements Serializable {

    private static final long serialVersionUID = -3010994063755554944L;

    /**
     * pdf操作域
     */
    private String field;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 横坐标
     */
    private float locationX;
    /**
     * 纵坐标
     */
    private float locationY;

    public ImageExt(float locationX, float locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
