package com.verdant.jtools.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author verdant
 * @since 2016/01/22
 */
public class ReflectUtils2 {
    private static Logger logger = LoggerFactory.getLogger(ReflectUtils2.class);

    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final String CGLIB_CLASS_SEPARATOR = "$$";


    public static String getClassName(Object o) {
        return o.getClass().getName();
    }

    /**
     * 将POJO对象转成Map
     *
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String, Object> generateMap(T obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            Object value = invokeGetter(obj, name);
            map.put(name, value);
        }
        return map;
    }

    /**
     * 将Map转成POJO对象
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T generateObj(Map<String, Object> map, Class<T> clazz) throws Exception {
        T obj = clazz.newInstance();
        for (String property : map.keySet()) {
            invokeSetter(obj, property, map.get(property));
        }
        return obj;
    }

    /**
     * 调用Getter方法
     *
     * @param obj
     * @param propertyName
     * @return
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        return invokeMethodMatched(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 调用Setter方法
     *
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        invokeMethodByName(obj, setterMethodName, new Object[]{value});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("Throw exception {}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("Throw exception {}", e.getMessage());
        }
    }

    /**
     * 匹配方法名和参数类型调用方法（适用于一次性调用的情况）
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return
     */
    public static Object invokeMethodMatched(final Object obj, final String methodName,
                                             final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethodMatched(obj, methodName, parameterTypes);
        return invokeMethod(method, obj, methodName, args);
    }

    /**
     * 只匹配方法名调用方法，如果有多个同名函数调用第一个
     *
     * @param obj
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        return invokeMethod(method, obj, methodName, args);
    }

    /**
     * 实际调用方法
     *
     * @param method
     * @param obj
     * @param methodName
     * @param args
     * @return
     */
    private static Object invokeMethod(Method method, final Object obj, String methodName, final Object[] args) {
        if (method == null) {
            logger.error("Class don't have method {}", methodName);
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("Call method {} failed", methodName);
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 获取同名属性
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");

        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod, 匹配方法名和参数类型
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getAccessibleMethodMatched(final Object obj, final String methodName,
                                                    final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod, 只匹配方法名
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 设置为Accessible，跳过安全检查
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 设置为Accessible，跳过安全检查
     *
     * @param method
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers())
                || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     *
     * @param e
     * @return
     */
    private static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }


    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<T> getClassGenericType(final Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class其父类的泛型参数类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic declaration, start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    public static Class getClassGenericType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn(clazz.getSimpleName() + "'s ParameterizedType size " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

}
