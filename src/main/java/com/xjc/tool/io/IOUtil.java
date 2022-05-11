package com.xjc.tool.io;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * IOUtil.java
 *
 * @author Xujc
 * @date 2022/1/4
 */
public class IOUtil {

    public MultipartFile outputStreamConvertMultipartFile(OutputStream outputStream) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
        return new MockMultipartFile(new Date() + "temp.jpg", new Date() + "temp.jpg", "", inputStream);
    }
}
