package com.xjc.tool.pdf.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.xjc.tool.pdf.api.PdfService;
import com.xjc.tool.pdf.model.ImageExt;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/6/20
 * @Descripetion
 */
@Service
public class PdfServiceImpl implements PdfService {

    private static final String KEY_PRE = "Picture";

    private static final List<String> TYPE_LIST = Lists.newArrayList("jpg", "png");

    private static Map<String, ImageExt> IMAGE_EXT = Maps.newHashMap();

    @PostConstruct
    public void init() {
        IMAGE_EXT.put("Picture1", new ImageExt(130.7821f, 430.287f));
        IMAGE_EXT.put("Picture2", new ImageExt(330.7821f, 430.287f));
        IMAGE_EXT.put("Picture3", new ImageExt(130.7821f, 30.287f));
        IMAGE_EXT.put("Picture4", new ImageExt(330.7821f, 30.287f));
    }

    @Override
    public void mulFile2One(List<File> filePathList, String targetPath) throws IOException {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        for (File f : filePathList) {
            if (f.exists() && f.isFile()) {
                mergePdf.addSource(f);
            }
        }
        mergePdf.setDestinationFileName(targetPath);
        mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

    @Override
    public List<String> mergeImage2One(List<File> imagePathList, boolean isPartition, int partition, Map<String, ImageExt> extMap, String outPath) throws IllegalArgumentException {
        List<String> result = Lists.newArrayList();
        List<List<File>> partitionList = Lists.newArrayList();
        if (isPartition) {
            if (!extMap.isEmpty()) {
                if (extMap.size() != partition) {
                    throw new IllegalArgumentException("分片数量与图片信息数量不一致");
                }
                IMAGE_EXT.clear();
                extMap.forEach((k, v) -> IMAGE_EXT.merge(k, v, (v1, v2) -> new ImageExt(v2.getField(), v2.getPath(), v2.getLocationX(), v2.getLocationY())));
            }
            partitionList = Lists.partition(imagePathList, partition);
        } else {
            partitionList.add(imagePathList);
        }
        int index = 0;
        StringBuilder outPathBuilder = new StringBuilder(outPath);
        List<ImageExt> imageExtList = Lists.newLinkedList();
        for (List<File> imagePath : partitionList) {
            checkImageType(imagePath);
            for (int i = 0; i < imagePath.size(); i++) {
                ImageExt coordinate = getCoordinate(KEY_PRE + (i + 1));
                coordinate.setField(KEY_PRE + (i + 1));
                coordinate.setPath(imagePath.get(i).getPath());
                imageExtList.add(coordinate);
            }
            // 图片合并成一张pdf， 四张图片为一张pdf
            if (!outPathBuilder.toString().endsWith(File.separator)) {
                outPathBuilder.append(File.separator);
            }
            String outputPdfPath = outPathBuilder.toString() + File.separator + "mergeRes" + index + ".pdf";
            String templatePath = Thread.currentThread().getContextClassLoader().getResource("pdfTemplate/merge_certificate_template.pdf").getPath();
            addImage(templatePath, outputPdfPath, imageExtList);
            imageExtList.clear();
            index++;
            result.add(outputPdfPath);
        }
        return result;
    }

    /**
     * 生成一个新的pdf
     *
     * @param templatePath  要操作的pdf路径
     * @param outputPdfPath 输出的pdf路径
     * @param imageExts     添加的图片信息
     */
    private void addImage(String templatePath, String outputPdfPath, List<ImageExt> imageExts) {
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        try {
            // 读取模板文件
            pdfReader = new PdfReader(templatePath);
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(outputPdfPath));
            for (ImageExt ext : imageExts) {
                // 获取pdf操作页面
                PdfContentByte overContent = pdfStamper.getOverContent(1);
                // 获取上传图片
                Image image = Image.getInstance(ext.getPath());
                // 图片旋转
                if (!getVerticalImage(image)) {
                    image.setRotationDegrees(270f);
                }
                // 图片缩放
                image.scalePercent(8f);
                // 左边距、底边距
                image.setAbsolutePosition(ext.getLocationX(), ext.getLocationY());
                overContent.addImage(image);
                overContent.stroke();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    /**
     * 判断是横版还是竖版图片
     *
     * @param image 图片
     * @return true是竖版，false是横版
     */
    private boolean getVerticalImage(Image image) {
        boolean isVertical = false;
        float height = image.getHeight();
        float width = image.getWidth();
        if (height > width) {
            isVertical = true;
        }
        return isVertical;
    }

    private ImageExt getCoordinate(String key) {
        return IMAGE_EXT.get(key);
    }

    /**
     * 图片格式过滤
     *
     * @param imagePath
     */
    private void checkImageType(List<File> imagePath) {
        List<String> typeList = Lists.newArrayList();
        imagePath.forEach(x -> typeList.add(getType(x.getName())));
        typeList.forEach(x -> {
            if (!TYPE_LIST.contains(x)) {
                throw new IllegalArgumentException("截图文件包内容不符合上传标准");
            }
        });
    }

    private String getType(String iamgeName) {
        return iamgeName.substring(iamgeName.lastIndexOf(".") + 1);
    }


}
