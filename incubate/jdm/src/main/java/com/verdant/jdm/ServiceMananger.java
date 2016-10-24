package com.verdant.jdm;

import com.verdant.jdm.service.BuildRegressionService;
import com.verdant.jdm.service.FuncTestService;
import com.verdant.jdm.service.UpdateRegressionService;

public abstract class ServiceMananger {
	public abstract FuncTestService getFuncTestService();
	public abstract BuildRegressionService getBuildRegressionService();
	public abstract UpdateRegressionService getUpdateRegressionService();
}
