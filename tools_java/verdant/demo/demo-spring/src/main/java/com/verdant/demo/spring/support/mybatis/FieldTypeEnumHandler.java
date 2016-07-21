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
 *    自定义TypeHandler处理器
 *    BaseTypeHandler已完成了null值判断
 *
 * @author verdant
 * @since 2016/07/06
 */
public class FieldTypeEnumHandler extends BaseTypeHandler<FieldTypeEnum> {

    private Class<FieldTypeEnum> type;
    private final FieldTypeEnum[] enums;

    // 根据数据库查询结果的列名获取其值，并转化成Enum
    @Override
    public FieldTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return this.locateEnum(i);
        }
    }

    // 根据数据库查询结果的列序号获取其值，并转化成Enum
    @Override
    public FieldTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return this.locateEnum(i);
        }
    }

    // 根据返回结果的列序号获取其值，并转化成Enum
    @Override
    public FieldTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return this.locateEnum(i);
        }
    }

    //将Enum类型转成要存入数据库的类型
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FieldTypeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    /**
     * 获取枚举的类型
     *
     * @param type Handle需处理的Enum类型
     */
    public FieldTypeEnumHandler(Class<FieldTypeEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
    }

    /**
     * 根据枚举某个字段的值返回枚举类型
     * @param code
     * @return
     */
    private FieldTypeEnum locateEnum(int code) {
        for (FieldTypeEnum fieldType : enums) {
            if (fieldType.getId() == code) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException("Unknown Enum field: " + code + ",please check" + type.getSimpleName());
    }
}
