package test;

import ioc.BeanDefinition;
import ioc.PropertyValue;
import ioc.RuntimeBeanReference;
import ioc.TypedStringValue;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import po.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 以面向过程的思维去实现IOC的相应过程
 * IOC思想：调用者，只负责Bean的使用，不负责Bean的创建
 */
public class TestSpringV2 {
    /**
     * 存储Bean实例
     * K：beanName
     * V：Bean实例
     */
    private Map<String, Object> singletonObjects = new HashMap<String, Object>();
    /**
     * 解析Bean实例存放Map
     * K：beanName
     * V：BeanDefination
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

    @Before
    public void before() {
        // XML解析，将BeanDefinition注册到beanDefinitions集合中
       String location="beans.xml";
        // 获取流对象
        InputStream inputStream = getInputStream(location);
        // 创建文档对象
        Document document=createDocument(inputStream);
        parseBeanDefinitions(document.getRootElement());
    }

    @Test
    public void test() {
        UserService userService = (UserService) getBean("userService");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "方方");
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }

    private void parseBeanDefinitions(Element rootElement) {
        // 获取<bean>和自定义标签（比如mvc:interceptors）
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if (name.equals("bean")) {
                // 解析默认标签，其实也就是bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签，比如aop:aspect标签
                parseCustomElement(element);
            }
        }
    }

    /**
     * 解析Bean标签
     * @param beanElement
     */
    private void parseDefaultElement(Element beanElement) {
        try {
            if (beanElement == null){
                return;
            }
            // 获取id属性
            String id = beanElement.attributeValue("id");
            // 获取name属性
            String name = beanElement.attributeValue("name");
            // 获取class属性
            String clazzName = beanElement.attributeValue("class");
            if (clazzName == null || "".equals(clazzName)) {
                return;
            }

            // 获取init-method属性
            String initMethod = beanElement.attributeValue("init-method");
            // 获取scope属性
            String scope = beanElement.attributeValue("scope");
            scope = scope != null && !scope.equals("") ? scope : "singleton";

            // 获取beanName
            String beanName = id == null ? name : id;
            Class<?> clazzType = Class.forName(clazzName);
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            // 创建BeanDefinition对象
            // 此次可以使用构建者模式进行优化
            BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            //TODO
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }

            // 注册BeanDefinition信息
            this.beanDefinitions.put(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if (propertyElement == null) {
            return;
        }
        // 获取name属性
        String name = propertyElement.attributeValue("name");
        // 获取value属性
        String value = propertyElement.attributeValue("value");
        // 获取ref属性
        String ref = propertyElement.attributeValue("ref");

        // 如果value和ref都有值，则返回
        if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
            return;
        }

        /**
         * PropertyValue就封装着一个property标签的信息
         */
        PropertyValue pv = null;

        if (value != null && !value.equals("")) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypedStringValue typeStringValue = new TypedStringValue(value);

            Class<?> targetType = getTypeByFieldName(beanDefinition.getClazzName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typeStringValue);
            beanDefinition.addPropertyValue(pv);
        } else if (ref != null && !ref.equals("")) {

            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefinition.addPropertyValue(pv);
        } else {
            return;
        }
    }

    private Class<?> getTypeByFieldName(String clazzName, String name) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private void parseCustomElement(Element element) {
        // TODO Auto-generated method stub

    }
    private Document createDocument(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getInputStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }




//    private UserServiceImpl getBean() {
    //以下代码只是UserServiceImpl的bean的创建过程-不通用
//        UserServiceImpl userService = new UserServiceImpl();
//        UserDaoImpl userDao = new UserDaoImpl();
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/oa?characterEncoding=utf-8&serverTimezone=UTC");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//        userDao.setDataSource(dataSource);
//        userService.setUserDao(userDao);
//        return userService;
//    }

    /**
     * 整体思路：以上的方法，由于写死的代码，只能创建UserServiceImpl实现类。 *
     * 如果想要获取任意对象的话，创建Bean的代码，就不能写死。----
     * 进行XML配置（bean的类型信息、bean属性信息、bean和bean的关系）---参考spring的配置文件 * 加载并解析对应的XML信息 --
     * BeanDefinition对象（只是相当于一个bean的信息，也就是说一个bean对应一个BeanDefinition） *
     * 根据beanName去获取对应的解析出来的信息（BeanDefinition），去创建Bean实例。 * 注意事项： *
     * <p>
     * 什么时机去解析对应的XML？--- 一般来说，XML只需要被解析一次即可。所以说，在创建Bean之前，需要一次性将XML解析完毕 *
     * <p>
     * 是否需要重复创建一个Bean？ ---
     * 一般来说，如果一个Bean是一个非状态Bean（Spring中管理的都是非状态bean），都是建议使用单例方式创建。 *
     * <p>
     * 什么时候创建Bean实例呢？第一次调用getBean方法的时候
     *
     * @param beanName
     * @return
     */
    private Object getBean(String beanName) {
        //先根据beanName去singletonObjects查询
        Object bean = singletonObjects.get(beanName);
        //如果有结果，则直接返回该实例
        if (bean != null) {
            return bean;
        }
        //如果没有结果，则需要创建Bean实例
        //现根据beanName去beanDefinations获取对应的BeanDefination（封装了该Bean创建时需要的相关信息）
        BeanDefinition beanDefination = beanDefinitions.get(beanName);
        if (beanDefination == null) {
            return null;
        }

        //根据BeanDefination中封装的信息来判断要创建的Bean是单例方式创建、还是多例方式创建
        if (beanDefination.isSingleton()) {
            //如果是单例，则需要将创建的Bean存储到singletonObjects
            bean = createBean(beanDefination);
            singletonObjects.put(beanName, bean);
        } else if (beanDefination.isPrototype()) {
            //如果是多例，则不需要将创建的Bean存储到singletonObjects
            bean = createBean(beanDefination);
        } else {
            //.....
        }


        return bean;
    }

    /**
     * 思路：
     * 1、Bean的实例化（new 对象）
     * 2、Bean的依赖注入
     * 3、Bean的初始化（调用初始化方法-init-method标签属性，或者实现了InitializingBean接口）
     *
     * @param beanDefination
     * @return
     */
    //TODO
    private Object createBean(BeanDefinition beanDefination) {
        Class<?> clazzType = beanDefination.getClazzType();
        if (clazzType == null) {
            return null;
        }
        //第一步:Bean的实例化（new 对象）
        Object bean = createBeanInstance(clazzType);
        //第二步:装配Bean、Bean的依赖注入
        populateBean(bean, beanDefination);
        //第三步：Bean的初始化
        initializeBean(bean, beanDefination);
        return bean;
    }

    /**
     * 创建Bean实例:默认使用无参构造
     *
     * @param clazzType
     * @return
     */
    private Object createBeanInstance(Class<?> clazzType) {
        try {
            Constructor<?> constructor = clazzType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 一般在该方法中会处理init-method标签和InitializingBean接口的初始化工作
     * 还会处理Aware接口
     *
     * @param bean
     * @param beanDefinition
     */
    private void initializeBean(Object bean, BeanDefinition beanDefinition) {
        //TODO 处理Aware接口（标记）
        //TODO  处理InitializingBean的初始化
        String initMethod = beanDefinition.getInitMethod();
        if (initMethod == null) {
            return;
        }
        //处理初始化方法init-method
        invokeInitMethod(bean, beanDefinition);
    }

    /**
     * 处理init-method
     *
     * @param beanDefination
     */
    private void invokeInitMethod(Object bean, BeanDefinition beanDefination) {

        String initMethod = beanDefination.getInitMethod();
        Class<?> clazzType = beanDefination.getClazzType();
        try {
            Method method = clazzType.getDeclaredMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 依赖注入
     *
     * @param bean
     * @param beanDefination
     */
    private void populateBean(Object bean, BeanDefinition beanDefination) {
        List<PropertyValue> propertyValues = beanDefination.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();
            //处理参数
            Object valueToUse = resolveValue(value);
            //设置属性
            setProperty(bean, name, valueToUse, beanDefination);
        }

    }


    /**
     * 设置属性
     *
     * @param bean
     * @param name
     * @param valueToUse
     * @param beanDefination
     */
    private void setProperty(Object bean, String name, Object valueToUse, BeanDefinition beanDefination) {
        Class<?> clazzType = beanDefination.getClazzType();
        try {
            Field field = clazzType.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object resolveValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue tsv = (TypedStringValue) value;
            String stringValue = tsv.getValue();
            Class<?> targetType = tsv.getTargetType();
            if (targetType == Integer.class) {
                return Integer.valueOf(stringValue);
            } else if (targetType == String.class) {
                return stringValue;
            }

        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference rbf = (RuntimeBeanReference) value;
            String ref = rbf.getRef();
            return getBean(ref);
        }
        return null;
    }


}
