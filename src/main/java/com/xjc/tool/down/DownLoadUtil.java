package com.xjc.tool.down;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Version 1.0
 * @ClassName DownLoadUtil
 * @Author jiachenXu
 * @Date 2020/3/3
 * @Description 下载
 */
@Component
@Slf4j
public class DownLoadUtil {

    public String downLoadFile(HttpServletRequest request, HttpServletResponse response) {
        // 读取文件地址
        File scFileDir = new File("F:\\redis\\");
        File[] trxFiles = scFileDir.listFiles();

        // 获取当前目录下的第几个文件
        String fileName = trxFiles[0].getName();
        String path = scFileDir.getPath();

        try {
            if (!StringUtils.isEmpty(fileName)) {
                File file = new File(path, fileName);
                if (file.exists()) {
                    response.reset();
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Length", "" + trxFiles[3].length());
                    response.setHeader("Content-Disposition", "attachment;filename=" +
                            new String(fileName.getBytes("gb2312"), "iso8859-1"));
                    try (InputStream is = new FileInputStream(file);
                         FilterInputStream fis = new BufferedInputStream(is, 1024);
                    ) {
                        OutputStream os = response.getOutputStream();
                        while (fis.read() != 0) {
                            os.write(fis.read());
                        }
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            log.error("文件下载出现异常", e);
        }

        return fileName;
    }

    public void downLoadFile(HttpServletRequest request, HttpServletResponse response, String urlStr) {
        int byteread = 0;
        try {
            String fileName = urlStr.trim().substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes("gb2312"), "iso8859-1"));
            try (InputStream inStream = conn.getInputStream();
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1204];
                while ((byteread = inStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteread);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
