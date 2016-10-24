package com.verdant.jdm.tools.calculus;

import com.verdant.jdm.common.Constants;

public class DefiniteIntegral {
	/**
	 * 求取多项式结果
	 * @param x
	 * @param coefficient
	 * @return
	 */
	public static double calcMultinomial(double x, double[] coefficient) {
		double predictResult = 0;
		if(null == coefficient) {
			throw new IllegalArgumentException(
					"Please execute the generateCoefficients function first!");
		}
		else {
			for(int i=0; i<coefficient.length ;i++)
				predictResult += coefficient[i] * Math.pow(x,i);
		}
		return predictResult;
	}
	/**
	 * 利用梯形法求多项式定积分
	 * @param low
	 * @param high
	 * @param coefficient
	 * @return
	 */
	public static double getDefiniteIntegrals(double low, double high, double[] coefficient) {
		double step = Math.abs(high - low) / Constants.IntegralPrecision;
		double integralSum = 0;
		for (double xi=low; xi<=high; xi=xi+step) {
			integralSum += ( calcMultinomial(xi,coefficient) + calcMultinomial((xi + step),coefficient) ) * step / 2;
		}
		integralSum = (double)Math.round(integralSum*10000)/10000;
		return integralSum;
	}
}