package com.verdant.dm.regression;

public interface Regressor {
    /**
     * 求取多项式回归的系数
     * @param maxPow
     */
    void generateCoefficients(int maxPow);

    /**
     * 获取系数列表
     * @return
     */
    double[] getCoefficients();

    /**
     * 获取预计值
     * @param x
     * @return
     */
    double getPredict(double x);
}