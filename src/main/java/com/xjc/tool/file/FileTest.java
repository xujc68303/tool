package com.xjc.tool.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.File;
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
        String zipPath = "C:\\Users\\Administrator\\Desktop\\test.zip";
        ZipUtil.unzip(zipPath, parentPath, Charset.forName("GBK"));
        File[] ls = FileUtil.ls(parentPath);
        for (int i = 0; i < ls.length; i++) {
            File file = ls[i];
            if (file.isDirectory()) {
                System.out.println("文件夹" + file.getName());
                File[] fileChilder = FileUtil.ls(file.getPath());
                for (int j = 0; j < fileChilder.length; j++) {
                    System.out.println(fileChilder[j].getPath());
                }
            } else {
                System.out.println("文件" + file.getName());
            }
        }


    }
}
