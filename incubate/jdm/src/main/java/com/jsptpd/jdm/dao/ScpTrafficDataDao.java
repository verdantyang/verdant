package com.jsptpd.jdm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jsptpd.jdm.po.Scp_traffic_data;

@Repository("ScpTrafficDataDao")
public class ScpTrafficDataDao extends BaseDao {
    public List<Scp_traffic_data> testQuery() {
    	System.err.println(sqlSessionTemplate);
    	return this.sqlSessionTemplate.selectList(
    			"com.jsptpd.jdm.dao.mapper.Scp_traffic_data.testQuery",1);  
    } 
    public List<Scp_traffic_data> statSiteFlow() {
    	System.err.println(sqlSessionTemplate);
    	return this.sqlSessionTemplate.selectList(
    			"com.jsptpd.jdm.dao.mapper.Scp_traffic_data.statSiteFlow",1);  
    } 
} 
