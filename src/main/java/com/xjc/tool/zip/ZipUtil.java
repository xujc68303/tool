package com.xjc.tool.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtil.java
 *
 * @author Xujc
 * @date 2021/10/19
 */
public class ZipUtil {

    public static void zipFileChannel(String zipUrl, String fileUrl) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipUrl));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            File path = new File(fileUrl);
            File[] files = path.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
                        String name = file.getName();
                        String type = name.substring(name.lastIndexOf(".") + 1);
                        if (!"jpg".equals(type) && !"png".equals(type)) {
                            name = name + ".jpg";
                        }
                        zipOut.putNextEntry(new ZipEntry(name));
                        fileChannel.transferTo(0, file.length(), writableByteChannel);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {

        ZipUtil.zipFileChannel("C:\\Users\\Xujc\\Desktop\\test.zip", "C:\\Users\\Xujc\\Desktop\\342");
    }
}
