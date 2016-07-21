package com.verdant.demo.common.base.convert;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Object和Map互转
 *
 * @author verdant
 * @since 2016/07/19
 */
public enum ObjectAndMap {
    //使用apache-commons的beanutils
    BeanutilsWay {
        @Override
        public Map<?, ?> objectToMap(Object obj) {
            if (obj == null)
                return null;
            return new BeanMap(obj);
        }

        @Override
        public Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
            if (map == null)
                return null;

            Object obj = beanClass.newInstance();
            BeanUtils.populate(obj, map);
            return obj;
        }
    },

    //使用java.beans
    IntrospectorWay {
        @Override
        public Map<?, ?> objectToMap(Object obj) throws Exception {
            if (obj == null)
                return null;

            Map<String, Object> map = new HashMap<>();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        }

        @Override
        public Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
            if (map == null)
                return null;

            Object obj = beanClass.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(obj, map.get(property.getName()));
                }
            }
            return obj;
        }
    },

    //使用java.reflect
    ReflectWay {
        @Override
        public Map<?, ?> objectToMap(Object obj) throws Exception {
            if (obj == null) {
                return null;
            }

            Map<String, Object> map = new HashMap<>();

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }

            return map;
        }

        @Override
        public Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
            if (map == null)
                return null;

            Object obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }

            return obj;
        }
    };

    public abstract Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception;

    public abstract Map<?, ?> objectToMap(Object obj) throws Exception;

}
