package com.xufangfang.spring.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 创建文档对象工具类
 */
public class DocumentUtils {

    public static Document createDocument(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            Document read = saxReader.read(inputStream);
            return read;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
