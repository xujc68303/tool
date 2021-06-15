package com.xjc.tool.image.service;

import com.xjc.tool.image.api.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void compression(String source, String target, double scale, double outputQuality, String format) throws Exception {
        if (scale > 1) {
            throw new IllegalArgumentException("压缩大小不能超过1");
        }
        Thumbnails.of(source).scale(scale).outputQuality(outputQuality).outputFormat(format).toFile(target);
    }

}
