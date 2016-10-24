package com.verdant.jdm.service;

import java.util.ArrayList;

import com.verdant.jdm.tools.calculus.DefiniteIntegral;
import com.verdant.jdm.tools.regression.MultiRegression;
import com.verdant.jdm.vo.DataPoint;
import com.verdant.jdm.vo.DateInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verdant.jdm.dao.ScpTrafficDataDao;

@Service
public class FuncTestService implements JdmService{
	private static final Logger logger = Logger.getLogger(FuncTestService.class);
	@Autowired
	ScpTrafficDataDao scpTrafficDataDao;
	public void excute() {
		int maxPow = 3;
		logger.info("Function test:");
		System.err.println("************DateInfo************");
		DateInfo time = new DateInfo(20130603);
		System.err.println("************Regression************");
		double[][] sample = {{-1,-1},{0,0},{1,1},{2,3},{3,5},{4,6}};
		//double[][] sample = {{0,1}};
		ArrayList<DataPoint> arraySample = new ArrayList<DataPoint>();
		System.err.println("Sample length:"+sample.length);
		for(int i=0;i<sample.length;i++) {
			DataPoint point = new DataPoint(sample[i][0], sample[i][1]);
			arraySample.add(point);
		}	
		MultiRegression tm = new MultiRegression(arraySample);
		try {
			tm.generateCoefficients(maxPow);
			int x=2;
			System.err.println("getPredict("+x+"):"+tm.getPredict(x));
			System.err.println("getRsquare:"+tm.getRsquare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		double coefs[] = new double[maxPow];
		coefs = tm.getCoefficients();
		for(int i=0; i<coefs.length; i++)
			System.err.println(coefs[i]);
		double sum = DefiniteIntegral.getDefiniteIntegrals(1,7,coefs);
		System.err.println(sum);
	}
}
