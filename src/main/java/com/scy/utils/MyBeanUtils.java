package com.scy.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/11 21:03
 * @Version 1.0
 */
public class MyBeanUtils {

    /**
     * 获得空属性的名称数组
     *
     * @param source 数据源
     * @return 其空属性的名称的数组 String[]
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor descriptor : descriptors) {
            String propertyName = descriptor.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }

        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
