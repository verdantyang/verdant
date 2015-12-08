package com.jsptpd.jdm;

import com.jsptpd.jdm.service.BuildRegressionService;
import com.jsptpd.jdm.service.FuncTestService;
import com.jsptpd.jdm.service.UpdateRegressionService;

public abstract class ServiceMananger {
	public abstract FuncTestService getFuncTestService();
	public abstract BuildRegressionService getBuildRegressionService();
	public abstract UpdateRegressionService getUpdateRegressionService();
}
