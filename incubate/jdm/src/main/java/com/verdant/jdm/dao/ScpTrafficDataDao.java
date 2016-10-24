package com.verdant.jdm.dao;

import java.util.List;

import com.verdant.jdm.po.Scp_traffic_data;
import org.springframework.stereotype.Repository;

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
