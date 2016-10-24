package com.verdant.jdm.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("BaseDao")
public abstract class BaseDao {
	public SqlSessionTemplate sqlSessionTemplate; 
	public SqlSessionTemplate getSqlSessionTemplate() {  
        return sqlSessionTemplate;  
    }    
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {  
        this.sqlSessionTemplate = sqlSessionTemplate;  
    }     
} 
