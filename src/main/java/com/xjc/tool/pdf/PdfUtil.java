package com.xjc.tool.pdf;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/5/29
 * @Descripetion
 */
@Slf4j
public class PdfUtil {

    private final static String NAME = "STSong-Light";

    private final static String ENCODING = "UniGB-UCS2-H";

    private static Map<String, ImageExt> IMAGEEXT = Maps.newLinkedHashMap();

    static {
        IMAGEEXT.put("Picture1", new ImageExt(47.7821f, 590.287f));
        IMAGEEXT.put("Picture2", new ImageExt(212.728f, 590.287f));
        IMAGEEXT.put("Picture3", new ImageExt(377.02f, 590.287f));
        IMAGEEXT.put("Picture4", new ImageExt(48.0528f, 390.044f));
        IMAGEEXT.put("Picture5", new ImageExt(212.728f, 390.044f));
        IMAGEEXT.put("Picture6", new ImageExt(377.02f, 390.044f));
    }

    public ImageExt getCoordinate(String path) {
        return IMAGEEXT.get(path);
    }

    /**
     * 插入单张图片
     *
     * @param templatePath  要操作的pdf路径
     * @param outputPdfPath 输出的pdf路径
     * @param imgPaths      添加的图片信息
     */
    public void insertImgToPdf(String templatePath, String outputPdfPath, List<ImageExt> imgPaths) {
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        try {
            // 读取模板文件
            pdfReader = new PdfReader(templatePath);
            // 生成一个新的pdf
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(outputPdfPath));
            // 获取pdf操作页面
            PdfContentByte overContent = pdfStamper.getOverContent(1);
            for (int i = 0; i < imgPaths.size(); i++) {
                ImageExt imageExt = imgPaths.get(i);
                // 获取上传图片
                Image image = Image.getInstance(imageExt.getPath());
                // 图片旋转
                if (i == 4 || i == 5) {
                    image.setRotationDegrees(90f);
                }
                // 图片缩放比例
                image.scalePercent(4.699f);
                // 左边距、底边距
                image.setAbsolutePosition(imageExt.getLocationX(), imageExt.getLocationY());
                overContent.addImage(image);
                overContent.stroke();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 这里先关闭pdfStamper再关闭pdfReader
            if (null != pdfStamper) {
                try {
                    pdfStamper.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != pdfReader) {
                pdfReader.close();
            }
        }
    }

    public static void main(String[] args) {
        PdfUtil pdfUtil = new PdfUtil();
        String templatePath = "F:\\workspace\\tool\\src\\main\\resources\\pdfTemplate\\mergeTemplate.pdf";
        String res = "F:\\workspace\\tool\\src\\main\\resources\\pdfTemplate\\mergeResult.pdf";
        String keyPre = "Picture";
        String imagePath = "F:\\workspace\\tool\\src\\main\\resources\\image";

        Map<String, String> field = Maps.newLinkedHashMap();
        File[] ls = FileUtil.ls(imagePath);
        for (int i = 0; i < ls.length; i++) {
            field.put(keyPre + (i + 1), ls[i].getPath());
        }
        List<ImageExt> imageList = Lists.newLinkedList();
        field.forEach((k, v) -> {
            ImageExt coordinate = pdfUtil.getCoordinate(k);
            coordinate.setField(k);
            coordinate.setPath(v);
            imageList.add(coordinate);
        });


        pdfUtil.insertImgToPdf(templatePath, res, imageList);
    }

    @Data
    static class ImageExt implements Serializable {
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


//    public void merge(String templatePath, String targetPath, Map<String, String> imageMap) {
//        PdfReader reader = null;
//        PdfStamper pdfStamper = null;
//
//        try (InputStream inputStream = new FileInputStream(FileUtil.file(templatePath))) {
//            // 读取模板文件
//            reader = new PdfReader(inputStream);
//            // 根据表单生成一个新的pdf
//            pdfStamper = new PdfStamper(reader, new FileOutputStream(targetPath));
//
//            // 读取pdf中的表单
//            AcroFields form = pdfStamper.getAcroFields();
//            form.addSubstitutionFont(BaseFont.createFont(NAME, ENCODING, BaseFont.NOT_EMBEDDED));
//
//            Iterator<Map.Entry<String, String>> iterator = imageMap.entrySet().iterator();
//            PdfContentByte pdfContentByte = null;
//            while (iterator.hasNext()) {
//                // 通过域名获取所在页和坐标
//                // key等于PDF文本域设置的域名
//                String key = iterator.next().getKey();
//                Rectangle signRect = form.getFieldPositions(key).get(0).position;
//                float x = signRect.getLeft();
//                float y = signRect.getBottom();
//
//                // 读图片
//                Image image = Image.getInstance(imageMap.get(key));
//                // 获取pdf操作页面
//                pdfContentByte = pdfStamper.getOverContent(1);
//                // 根据域的大小缩放图片
//                float width = signRect.getWidth();
//                float height = signRect.getHeight();
//                image.scaleToFit(width, height);
//                // 添加图片
//                image.setAbsolutePosition(x, y);
//                pdfContentByte.addImage(image);
//            }
//            pdfContentByte.stroke();
//            //关闭填充
//            pdfStamper.setFormFlattening(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
