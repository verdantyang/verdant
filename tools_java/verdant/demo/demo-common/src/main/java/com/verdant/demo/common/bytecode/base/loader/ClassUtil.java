package com.verdant.demo.common.bytecode.base.loader;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    public static final String ARRAY_SUFFIX = "[]";

    public static final Class EMPTY_CLASS_ARRAY[] = new Class[0];

    private static final char INNER_CLASS_SEPARATOR_CHAR = 36;

    private static final char PACKAGE_SEPARATOR_CHAR = 46;

    private static Class PRIMITIVE_CLASSES[];

    static {
        PRIMITIVE_CLASSES = (new Class[]{
                Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
        });
    }

    public static String addResourcePathToPackagePath(Class clazz, String resourceName) {
        if (!resourceName.startsWith("/"))
            return (new StringBuilder()).append(classPackageAsResourcePath(clazz)).append("/").append(resourceName).toString();
        else
            return (new StringBuilder()).append(classPackageAsResourcePath(clazz)).append(resourceName).toString();
    }

    public static String classPackageAsResourcePath(Class clazz) {
        if (clazz == null || clazz.getPackage() == null)
            return "";
        else
            return clazz.getPackage().getName().replace('.', '/');
    }

    private static List getAllInterfacesExclude(Class clazz, String itfNames[], List list) {
        if (list == null)
            list = new ArrayList();
        if (clazz != null) {
            Class interfaces[] = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                boolean needAdded = true;
                if (list.contains(interfaces[i]))
                    continue;
                if (itfNames != null) {
                    for (int j = 0; j < itfNames.length && needAdded; j++) {
                        if (!interfaces[i].getName().startsWith(itfNames[j]))
                            continue;
                        if (clazz.isInterface())
                            list.remove(clazz);
                        needAdded = false;
                    }

                }
                if (needAdded) {
                    list.add(interfaces[i]);
                    getAllInterfacesExclude(interfaces[i], itfNames, list);
                }
            }

            clazz = clazz.getSuperclass();
            getAllInterfacesExclude(clazz, itfNames, list);
        }
        return list;
    }

    public static Constructor getConstructor(Class clz, Class expectedTypes[]) {
        Constructor constructor = null;
        try {
            Constructor constructors[] = clz.getConstructors();
            for (int i = 0; i < constructors.length; i++) {
                Constructor creator = constructors[i];
                if (isAssignable(expectedTypes, creator.getParameterTypes()))
                    if (constructor == null)
                        constructor = creator;
                    else if (isAssignable(creator.getParameterTypes(), constructor.getParameterTypes()))
                        constructor = creator;
            }

        } catch (Throwable thr) {
            String msg = (new StringBuilder()).append("Class: ").append(clz.getName()).append(", ").append(clz.getProtectionDomain().getCodeSource().getLocation()).append(", ").append(clz.getClassLoader()).toString();
            throw new RuntimeException(msg, thr);
        }
        return constructor;
    }

    public static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {

            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }

        });
    }

    public static Class[] getInterfaces(Class clazz) {
        List interfaces = new ArrayList();
        interfaces = getAllInterfacesExclude(clazz, null, interfaces);
        return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces.size()]);
    }

    public static Class[] getInterfaces(Class clazz, String excludes[]) {
        List interfaces = new ArrayList();
        interfaces = getAllInterfacesExclude(clazz, excludes, interfaces);
        return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces.size()]);
    }

    public static Method getMethod(Class clz, String methodName, Class expectedTypes[])
            throws NoSuchMethodException {
        Method method = null;
        try {
            method = clz.getMethod(methodName, expectedTypes);
        } catch (NoSuchMethodException e) {
            Method methods[] = clz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method _method = methods[i];
                if (!_method.getName().equals(methodName) || !isAssignable(expectedTypes, _method.getParameterTypes()))
                    continue;
                if (method == null) {
                    method = _method;
                    continue;
                }
                if (isAssignable(_method.getParameterTypes(), method.getParameterTypes()))
                    method = _method;
            }

            if (method == null)
                throw e;
        }
        return method;
    }

    public static String getShortName(Class clazz) {
        return getShortName(clazz.getName());
    }

    public static String getShortName(String className) {
        char charArray[] = className.toCharArray();
        int lastDot = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '.') {
                lastDot = i + 1;
                continue;
            }
            if (charArray[i] == '$')
                charArray[i] = '.';
        }

        return new String(charArray, lastDot, charArray.length - lastDot);
    }

    public static Method getStaticMethod(Class clazz, String methodName, Class args[]) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, args);
            if ((method.getModifiers() & 8) != 0)
                return method;
        } catch (NoSuchMethodException ex) {
        }
        return null;
    }

    public static boolean hasAtLeastOneMethodWithName(Class clazz, String methodName) {
        do {
            for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
                Method method = clazz.getDeclaredMethods()[i];
                if (methodName.equals(method.getName()))
                    return true;
            }

            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return false;
    }

    public static boolean isAssignable(Class cls, Class toClass) {
        if (toClass == null)
            return false;
        if (cls == null)
            return !toClass.isPrimitive();
        if (cls.equals(toClass))
            return true;
        if (cls.isPrimitive()) {
            if (!toClass.isPrimitive())
                return false;
            if (Integer.TYPE.equals(cls))
                return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if (Long.TYPE.equals(cls))
                return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if (Boolean.TYPE.equals(cls))
                return false;
            if (Double.TYPE.equals(cls))
                return false;
            if (Float.TYPE.equals(cls))
                return Double.TYPE.equals(toClass);
            if (Character.TYPE.equals(cls))
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if (Short.TYPE.equals(cls))
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            if (Byte.TYPE.equals(cls))
                return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            else
                return false;
        } else {
            return toClass.isAssignableFrom(cls);
        }
    }

    public static boolean isAssignable(Class classArray[], Class toClassArray[]) {
        if (classArray.length != toClassArray.length)
            return false;
        if (classArray == null)
            classArray = EMPTY_CLASS_ARRAY;
        if (toClassArray == null)
            toClassArray = EMPTY_CLASS_ARRAY;
        for (int i = 0; i < classArray.length; i++)
            if (!isAssignable(classArray[i], toClassArray[i]))
                return false;

        return true;
    }

    public static boolean isAssignableFrom(Class original, Class checkedClasses[]) {
        if (checkedClasses == null)
            return false;
        for (int i = 0; i < checkedClasses.length; i++)
            if (original.isAssignableFrom(checkedClasses[i]))
                return true;

        return false;
    }

    public static Class loadClass(final String name)
            throws ClassNotFoundException {
        if (name.length() <= 8) {
            for (int i = 0; i < PRIMITIVE_CLASSES.length; i++) {
                Class clazz = PRIMITIVE_CLASSES[i];
                if (clazz.getName().equals(name))
                    return clazz;
            }

        }
        if (name.endsWith("[]")) {
            String elementClassName = name.substring(0, name.length() - "[]".length());
            Class elementClass = loadClass(elementClassName);
            return Array.newInstance(elementClass, 0).getClass();
        }
        Class result = null;
        try {
            result = Class.forName(name);
            if (result != null) {
                return result;
            }
        } catch (ClassNotFoundException e) {

        }

        Object obj = AccessController.doPrivileged(new PrivilegedAction() {

            @Override
            public Object run() {
                ClassLoader threadCL = ClassUtil.getContextClassLoader();
                if (threadCL != null)
                    try {
                        return threadCL.loadClass(name);
                    } catch (ClassNotFoundException ex) {
                        return ex;
                    }
                else
                    return null;
            }

        });
        if (obj instanceof Class)
            return (Class) obj;
        else
            throw new ClassNotFoundException("Not Found the class which name of" + name);
    }

    public ClassUtil() {
    }
}
