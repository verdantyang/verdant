package com.jsptpd.jdm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jsptpd.jdm.service.JdmService;

public class App {
	@SuppressWarnings("resource")
	public static void main( String[] args ) {
    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	ServiceMananger manager = context.getBean("serviceMananger",ServiceMananger.class);
    	JdmService funcTestService = manager.getFuncTestService(); 
    	JdmService buildRegressionService = manager.getBuildRegressionService(); 
    	JdmService updateRegressionService = manager.getUpdateRegressionService(); 
    	//funcTestService.excute();
    	buildRegressionService.excute();
    }
}
