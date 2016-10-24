package com.verdant.dm.regression;

import java.util.ArrayList;

import Jama.Matrix;

import com.verdant.dm.math.Determinant;
import com.verdant.dm.common.DataPoint;
import com.verdant.dm.utils.DebugLog;

public class MultiRegression extends AbstractRegression {

    private static final long serialVersionUID = -4958793056477041485L;
    private ArrayList<Double> vectorX;
    private ArrayList<Double> vectorY;

    /**
     * coefficients
     */
    private double[] coefficient;

    /**
     * Constructor
     *
     * @param oriData
     */
    public MultiRegression(ArrayList<DataPoint> oriData) {
        dataPair = oriData;
        vectorX = new ArrayList<Double>();
        vectorY = new ArrayList<Double>();
        for (int i = 0; i < dataPair.size(); i++) {
            vectorX.add(dataPair.get(i).x);
            vectorY.add(dataPair.get(i).y);
        }
    }

    /**
     * 最小二乘法求取多项式回归的系数
     *
     * @param maxPow
     * @return
     */
    @Override
    public void generateCoefficients(int maxPow) {
        Matrix phiT = Determinant.getVandermondeMatrix(vectorX, maxPow);
        DebugLog.devPrintMatrix("phiT", phiT);
        Matrix phi = phiT.transpose();
        double[][] valsY = new double[vectorY.size()][1];
        for (int m = 0; m < vectorX.size(); m++)
            valsY[m][0] = vectorY.get(m);
        Matrix matrixY = new Matrix(valsY);
        Matrix left = phi.times(phiT);
        Matrix right = phi.times(matrixY);
        DebugLog.devPrintMatrix("left", left);
        DebugLog.devPrintMatrix("right", right);
        try {
            Matrix coefMatrix = left.solve(right);
            coefficient = new double[maxPow + 1];
            for (int i = 0; i <= maxPow; i++) {
                coefficient[i] = coefMatrix.get(i, 0);
            }
            for (int i = 0; i < coefficient.length; i++) {
                DebugLog.devPrintString("coefficient:" + coefficient[i]);
            }
            DebugLog.devPrintMatrix("coefMatrix", coefMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系数列表
     *
     * @return
     */
    @Override
    public double[] getCoefficients() {
        if (null == coefficient) {
            throw new IllegalArgumentException(
                    "Please execute the generateCoefficients function first!");
        } else
            return coefficient;
    }

    /**
     * 根据传入的x预估y值
     *
     * @param x
     * @return
     */
    @Override
    public double getPredict(double x) {
        double predictResult = 0;
        if (null == coefficient) {
            throw new IllegalArgumentException(
                    "Please execute the generateCoefficients function first!");
        } else {
            for (int i = 0; i < coefficient.length; i++)
                predictResult += coefficient[i] * Math.pow(x, i);
        }
        return predictResult;
    }
}