package com.verdant.demo.spring.support;

import java.util.Date;

/**
 * 字段类型枚举类型
 *
 * @author verdant
 * @since 2016/07/06
 */
public enum FieldTypeEnum {
    Boolean(0, "Boolean", Boolean.class),
    Integer(1, "Integer", Integer.class),
    Long(2, "Long", Long.class),
    Double(3, "Double", Double.class),
    String(4, "String", String.class),
    Date(5, "Date",Date.class);

    private final int id;
    private final String name;
    private final Class<?> clazz;

    FieldTypeEnum(int id, String type, Class<?> clazz) {
        this.id = id;
        this.name = type;
        this.clazz = clazz;
    }

    public static FieldTypeEnum get(int id) {
        for (FieldTypeEnum e : values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }


    public static Class<?> getClazzByType(String type) {
        for (FieldTypeEnum e : values()) {
            if (e.getName().equals(type)) {
                return e.getClazz();
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}
