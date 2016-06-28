package com.verdant.jtools.common.orm.model;

import java.util.Arrays;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/03/14
 * Desc: Model 数据库表映射对象
 */
public abstract class TableModel {
    /**
     * 主键集合
     */
    protected transient List<String> primaries;

    /**
     * 获取实体类主键
     */
    public abstract Object getPrimary();

    /**
     * 根据类型转换主键
     * 主键生成完为String类型 关联表依然是Integer或Long等等
     * 关联的实体 set主键时 不需要手动Integer.valueOf等等转换
     */
    public <T> T getPrimaryByClazz(Class<T> clazz) {
        Object obj = getPrimary();
        if (obj == null) {
            return null;
        }
        if (clazz == String.class) {
            return (T) String.valueOf(obj);
        }
        if (clazz == Integer.class) {
            return (T) Integer.valueOf(String.valueOf(obj));
        }
        return (T) obj;
    }

    public List<String> getPrimaries() {
        if (this.getPrimary() != null) {
            List<String> primarys = Arrays.asList(String.valueOf(this.getPrimary()).split(","));
            return primarys.isEmpty() ? null : primarys;
        }
        return null;
    }

    public enum Determine {

        NO(0, "否"),
        YES(1, "是"),;
        /**
         * 编号
         */
        private Integer code;

        /**
         * 描述
         */
        private String desc;

        private Determine(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }


        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
