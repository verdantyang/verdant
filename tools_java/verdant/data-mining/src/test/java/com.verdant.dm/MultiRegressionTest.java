package com.verdant.dm;


import com.verdant.dm.entity.DataPoint;
import com.verdant.dm.regression.AbstractRegression;
import com.verdant.dm.regression.impl.MultiRegression;
import org.junit.Test;

import java.util.ArrayList;

public class MultiRegressionTest {
    @Test
    public void main() throws Exception {
        //double[][] sample = {{0,1},{1,0},{2,1}};
        double[][] sample = {{-1, -1}, {0, 0}, {1, 1}, {2, 3}, {3, 5}, {6, 6}};
        ArrayList<DataPoint> arraySample = new ArrayList<>();
        for (int i = 0; i < sample.length; i++) {
            DataPoint point = new DataPoint(sample[i][0], sample[i][1]);
            arraySample.add(point);
        }
        AbstractRegression tm = new MultiRegression(arraySample);
        tm.generateCoefficients(3);
        int x = 2;
        System.err.println("sample length:" + sample.length);
        System.err.println("getPredict(" + x + "):" + tm.getPredict(x));
        System.err.println("getRsquare:" + tm.getRsquare());
    }
}