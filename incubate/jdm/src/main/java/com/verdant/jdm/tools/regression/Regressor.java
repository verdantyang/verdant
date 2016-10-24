package com.verdant.jdm.tools.regression;

import java.io.Serializable;

public interface Regressor extends Serializable {
	public void generateCoefficients(int maxPow) throws Exception;
	public double[] getCoefficients() throws Exception;
	public double getPredict(double x) throws Exception;
}