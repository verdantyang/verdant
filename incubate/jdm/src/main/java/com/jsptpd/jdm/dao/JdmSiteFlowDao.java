package com.jsptpd.jdm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jsptpd.jdm.po.Jdm_site_flow;

@Repository("JdmSiteFlowDao")
public class JdmSiteFlowDao extends BaseDao {
    public void insertSiteFlow() {
    	this.sqlSessionTemplate.insert(
    		"com.jsptpd.jdm.dao.mapper.Jdm_site_flow.insetSiteFlow"); 
    } 
    public List<String> getSiteList() {
    	return this.sqlSessionTemplate.selectList(
    			"com.jsptpd.jdm.dao.mapper.Jdm_site_flow.getSiteList");  
    } 
    public List<Jdm_site_flow> getFlowList(int startDay, int endDay, String siteID) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("startDay", startDay);
    	map.put("endDay", endDay);
    	map.put("siteID", siteID);
    	return this.sqlSessionTemplate.selectList(
    			"com.jsptpd.jdm.dao.mapper.Jdm_site_flow.getFlowList",map); 
    }
} 
