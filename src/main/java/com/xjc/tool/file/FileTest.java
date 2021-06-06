package com.xjc.tool.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import static cn.hutool.core.io.FileUtil.FILE_SEPARATOR;

/**
 * @Author jiachenxu
 * @Date 2021/6/6
 * @Descripetion
 */
public class FileTest {

    public static void main(String[] args) {
        String parentPath = FILE_SEPARATOR + "merge-service";
        FileUtil.mkdir(parentPath);
        String id = FILE_SEPARATOR + "72";
        FileUtil.mkdir(parentPath + id);

        String wordPath = parentPath + id + FILE_SEPARATOR + "word" + FILE_SEPARATOR + "merge";
        FileUtil.mkdir(wordPath);
        String pdfPath = parentPath + id + FILE_SEPARATOR + "pdf" + FILE_SEPARATOR + "merge";
        FileUtil.mkdir(pdfPath);
        String zipPath = parentPath + id + FILE_SEPARATOR + "zip" + FILE_SEPARATOR + "image";
        FileUtil.mkdir(zipPath);

        String zipReqPath = "C:\\Users\\Administrator\\Desktop\\格列兹运动专营店—洛亚.zip";
        ZipUtil.unzip(zipReqPath, zipPath, Charset.forName("GBK"));

        File[] zipImageLs = FileUtil.ls(zipPath);
        for (int i = 0; i < zipImageLs.length; i++) {
            File file = zipImageLs[i];
            String name = file.getName().trim();
            String type = FileUtil.getSuffix(name);
            if (!type.equalsIgnoreCase("pdf")) {
                continue;
            } else {
                if (name.contains("截图")) {
                    FileUtil.move(file, FileUtil.file(wordPath), true);
                    continue;
                }
                FileUtil.move(file, FileUtil.file(pdfPath), true);
            }
        }
        FileUtil.del(parentPath + id);

        URL resource = FileTest.class.getClassLoader().getResource("image/IMG_20210507_113021.jpg");
        String path = resource.getPath();
        System.out.println(path);

        URL resource1 = Thread.currentThread().getContextClassLoader().getResource("image/IMG_20210507_113021.jpg");
        String path1 = resource1.getPath();
        System.out.println(path1);

        URL systemResource = ClassLoader.getSystemResource("image/IMG_20210507_113021.jpg");
        String path2 = systemResource.getPath();
        System.out.println(path2);

        URL resource2 = ClassLoader.getSystemClassLoader().getResource("image/IMG_20210507_113021.jpg");
        String path3 = resource2.getPath();
        System.out.println(path3);


    }
}
