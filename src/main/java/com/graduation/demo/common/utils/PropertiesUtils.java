package com.graduation.demo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {


    private static String filename = "application.properties";

    private static String rootPath = "property";

    private static Map<String, PropertiesUtils> propMap = null;

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static Properties props = new Properties();

    /**
     * 资源加载
     *
     * @param input
     * @throws IOException
     */
    public void load(InputStream input) throws IOException {
        this.props.load(input);
        transCode();
    }

    /**
     * 根据文件名称或者加载对象
     *
     * @param filename
     * @return
     */
    public static Properties getInstance(String resourceName) {
        if (propMap == null) {
            propMap = new HashMap();
        }

        if (resourceName != null && !"".equals(resourceName)) {
            filename = resourceName;
        }

        if (propMap.get(filename) == null) {
            PropertiesUtils propsUtil = new PropertiesUtils();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(rootPath + File.separator + filename);
            try {
                if (is != null) {
                    propsUtil.load(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("读取配置文件conf.properties出错！", e);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            propMap.put(filename, propsUtil);
        }
        return ((PropertiesUtils) propMap.get(filename)).getProps();
    }

    // 转码处理
    private void transCode() {
        Iterator iter = this.props.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String newValue = null;
            try {
                String propertiesFileEncode = "utf-8";
                newValue = new String(this.props.getProperty(key).getBytes(
                        "ISO-8859-1"), propertiesFileEncode);
            } catch (UnsupportedEncodingException e) {
                newValue = this.props.getProperty(key);
            }
            this.props.setProperty(key, newValue);
        }
    }

    /**
     * 根据key，取得对应的value值
     * 默认加载application.properties文件
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        if (key == null) {
            return "";
        }
        String value = getInstance(filename).getProperty(key);
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    public Properties getProps() {
        return this.props;
    }

    /**
     * 根据filename名称获取相应的值
     *
     * @param filename
     * @param key
     * @return
     */
    public static String getValue(String filename, String key) {
        return getInstance(filename).getProperty(key);
    }

}
