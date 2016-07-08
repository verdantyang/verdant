package com.verdant.demo.spring.support.mybatis;

import com.verdant.demo.spring.support.FieldTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis枚举类型转换
 *
 * @author verdant
 * @since 2016/07/06
 */
public class FieldTypeEnumHandler extends BaseTypeHandler<FieldTypeEnum> {

    private Class<FieldTypeEnum> type;

    private final FieldTypeEnum[] enums;

    /**
     * 设置配置文件设置的转换类以及枚举类内容，供其他方法更便捷高效的实现
     *
     * @param type 配置文件中设置的转换类
     */
    public FieldTypeEnumHandler(Class<FieldTypeEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
    }

    @Override
    public FieldTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 根据数据库存储类型决定获取类型，本handler中数据库中存放INT类型
        int i = rs.getInt(columnName);

        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的id值，定位EnumStatus子类
            return locateEnumStatus(i);
        }
    }

    @Override
    public FieldTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本handler中数据库中存放INT类型
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位FieldTypeEnum子类
            return locateEnumStatus(i);
        }
    }

    @Override
    public FieldTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本handler中数据库中存放INT类型
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的code值，定位FieldTypeEnum子类
            return locateEnumStatus(i);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FieldTypeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, parameter.getId());
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     *
     * @param code 数据库中存储的自定义code属性
     * @return code对应的枚举类
     */
    private FieldTypeEnum locateEnumStatus(int code) {
        for (FieldTypeEnum fieldType : enums) {
            if (fieldType.getId() == code) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对" + type.getSimpleName());
    }
}
