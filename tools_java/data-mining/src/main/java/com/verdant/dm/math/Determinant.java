package com.verdant.dm.math;

import Jama.Matrix;

import java.util.ArrayList;

/**
 * Determinant
 *
 * @author verdant
 * @since 2016/10/24
 */
public class Determinant {
    /**
     * 求取范德蒙行列式
     *
     * @param vectorX
     * @param column
     * @return
     */
    public static Matrix getVandermondeMatrix(ArrayList<Double> vectorX, int column) {
        double[][] vals = new double[vectorX.size()][column + 1];
        for (int m = 0; m < vectorX.size(); m++)
            vals[m][0] = 1;
        for (int m = 0; m < vectorX.size(); m++)
            for (int n = 1; n <= column; n++)
                vals[m][n] = Math.pow(vectorX.get(m), n);
        return new Matrix(vals);
    }
}
