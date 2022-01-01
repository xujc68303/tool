package com.xjc.tool.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

/**
 * @Author jiachenxu
 * @Date 2022/1/1
 * @Descripetion
 */
@Component
public class QrUtil {

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static final String FORMAT = "png";

    /**
     * 生成二维码图片
     *
     * @param contents
     * @param response
     * @throws Exception
     */
    public void generate(String contents, HttpServletResponse response) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 2);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        BitMatrix byteMatrix = new MultiFormatWriter().encode(new String(contents.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.ISO_8859_1), BarcodeFormat.QR_CODE, 300, 300, hints);
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, byteMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        if (!ImageIO.write(image, FORMAT, response.getOutputStream())) {
            throw new IOException("could not write an image of format " + FORMAT);
        }
    }

}
