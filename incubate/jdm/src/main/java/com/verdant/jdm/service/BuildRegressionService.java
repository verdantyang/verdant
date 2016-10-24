package com.verdant.jdm.service;

import java.util.ArrayList;
import java.util.List;

import com.verdant.jdm.dao.JdmSiteFlowDao;
import com.verdant.jdm.tools.ToolsUtil;
import com.verdant.jdm.tools.regression.MultiRegression;
import com.verdant.jdm.vo.DataPoint;
import com.verdant.jdm.vo.DateInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verdant.jdm.common.Constants;
import com.verdant.jdm.dao.ScpTrafficDataDao;
import com.verdant.jdm.po.Jdm_site_flow;

@Service
public class BuildRegressionService implements JdmService{
	private static final Logger logger = Logger.getLogger(BuildRegressionService.class);
	@Autowired
	ScpTrafficDataDao scpTrafficDataDao;
	@Autowired
    JdmSiteFlowDao jdmSiteFlowDao;
	public void excute() {
		logger.info("BuildRegressionService start:");
		//jdmSiteFlowDao.insertSiteFlow();
		List<String> siteList = jdmSiteFlowDao.getSiteList();		
		int today = ToolsUtil.getTodayID();
		today = 20130610;
		if ((today-Constants.StartDateID)/10000 <= 1) {
			DateInfo startDay = new DateInfo(Constants.StartDateID);
			DateInfo nextWeekDay= startDay;
			while(nextWeekDay.getDate_id() < today){			
				System.err.println(startDay.getDay_id());
				int endDay = startDay.dayAdd(7-startDay.getDay_id());
				System.err.println(endDay);
				nextWeekDay= new DateInfo(startDay.dayAdd(8-startDay.getDay_id()));
				for(String item : siteList) {
					System.err.println(item);
					buildRegression(startDay.getDate_id(), endDay, item);
				}
				startDay = nextWeekDay;
			}
		}
		else {
			//Average code
		}
	}
	
	public void buildRegression(int startDay, int endDay, String siteID) {
		List<Jdm_site_flow> flowList = jdmSiteFlowDao.getFlowList(startDay, endDay, siteID);
		ArrayList<DataPoint> arrayNH = new ArrayList<DataPoint>();
		ArrayList<DataPoint> arrayHN = new ArrayList<DataPoint>();
		for(Jdm_site_flow item: flowList) {
			if(Constants.DirectionNH == item.getDirection_id()) {
				arrayNH.add(new DataPoint(getDayNO(item.getDate_id()),item.getTotal_flow()));
			}
			else if(Constants.DirectionHN == item.getDirection_id()) {
				arrayHN.add(new DataPoint(getDayNO(item.getDate_id()),item.getTotal_flow()));
			}		
		}
		buildRegression(arrayNH, Constants.DirectionNH);
		buildRegression(arrayHN, Constants.DirectionHN);
	}
	
	public void buildRegression(ArrayList<DataPoint> array, int directID) {	
		if(0 == array.size())
			return;
		MultiRegression tm = new MultiRegression(array);
		try {
			System.err.println(array.size());
			tm.generateCoefficients(array.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		double coefs[] = new double[array.size()];
		coefs = tm.getCoefficients();
		for(int i=0; i<coefs.length; i++)
			System.err.println(coefs[i]);
	}
	
	public int getDayNO(int dateID) {
		DateInfo day = new DateInfo(dateID);
		return day.getDay_id();
	}
}

