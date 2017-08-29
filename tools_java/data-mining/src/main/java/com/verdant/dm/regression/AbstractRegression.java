package com.verdant.dm.regression;

import java.util.ArrayList;

import com.verdant.dm.entity.DataPoint;
import com.verdant.dm.utils.DebugLog;

public abstract class AbstractRegression implements Regressor {

    protected ArrayList<DataPoint> dataPair;
    /**
     * coefficients
     */
    protected double[] coefficient;

    /**
     * Get the evaluation standard R-square
     *
     * @return
     * @throws Exception
     */
    public double getRsquare() {
        double sumY = 0;
        double deltaTrueY = 0;
        double deltaBarY = 0;
        double Rsquare;

        for (DataPoint dataPoint : dataPair) {
            sumY += dataPoint.getY();
        }
        double EY = sumY / dataPair.size();
        DebugLog.devPrintString("y-:" + EY);
        for (DataPoint dataPoint : dataPair) {
            DebugLog.devPrintString("xi:" + dataPoint.getX() + "  " +
                    "yi:" + dataPoint.getY() + "  " +
                    "yi^:" + getPredict(dataPoint.getX()));
            deltaTrueY += Math.pow((dataPoint.getY() - getPredict(dataPoint.getX())), 2);
            deltaBarY += (dataPoint.getY() - EY) * (dataPoint.getY() - EY);
        }
        Rsquare = 1 - deltaTrueY / deltaBarY;
        Rsquare = (double) Math.round(Rsquare * 10000) / 10000;
        return Rsquare;
    }

    public abstract void generateCoefficients(int maxPow);

    public abstract double[] getCoefficients();

    public abstract double getPredict(double x);

}