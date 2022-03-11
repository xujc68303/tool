package com.xjc.tool.image.service;

import com.xjc.tool.image.api.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void compression(String source, String target, double scale, double outputQuality, String format) throws IOException {
        if (scale > 1) {
            throw new IllegalArgumentException("压缩大小不能超过1");
        }
        Thumbnails.of(source).scale(scale).outputQuality(outputQuality).outputFormat(format).toFile(target);
    }

    @Override
    public void scaling(String source, String target, Integer width, Integer height, String format) throws IOException {
        Thumbnails.of(source).size(width, height).outputFormat(format).toFile(target);
    }
}
