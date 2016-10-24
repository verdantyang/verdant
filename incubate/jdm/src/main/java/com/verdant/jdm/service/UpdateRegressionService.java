package com.verdant.jdm.service;

import java.util.List;

import com.verdant.jdm.po.Scp_traffic_data;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verdant.jdm.dao.ScpTrafficDataDao;

@Service
public class UpdateRegressionService implements JdmService{
	private static final Logger logger = Logger.getLogger(UpdateRegressionService.class);
	@Autowired
	ScpTrafficDataDao scpTrafficDataDao;
	public void excute() {
		logger.info("Get traffic data:");
		List<Scp_traffic_data> result = scpTrafficDataDao.testQuery();
		for(Scp_traffic_data item : result) {
			System.err.println(item.getDate_id());
		}	
	}
}
