package com.xufangfang.spring.framework.reader;


import com.xufangfang.spring.framework.registry.BeanDefinitionRegistry;
import com.xufangfang.spring.framework.utils.DocumentUtils;
import org.dom4j.Document;


import java.io.InputStream;


/**
 * BeanDefinition阅读器
 * 专门提供针对XML文件，解析并注册BeanDefinition的功能
 */
public class XmlBeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    //#registerBeanDefinitions
    public void registerBeanDefinitions(InputStream inputStream) {
        Document document = DocumentUtils.createDocument(inputStream);
        XmlBeanDefinitionDocumentReader xmlBeanDefinitionDocumentReader=new XmlBeanDefinitionDocumentReader(beanDefinitionRegistry);
        xmlBeanDefinitionDocumentReader.loadDefinitions(document.getRootElement());

    }



}
