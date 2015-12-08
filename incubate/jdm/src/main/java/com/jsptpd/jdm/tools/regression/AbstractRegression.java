package com.jsptpd.jdm.tools.regression;

import java.util.ArrayList;

import com.jsptpd.jdm.tools.ToolsUtil;
import com.jsptpd.jdm.vo.DataPoint;


public abstract class AbstractRegression implements Regressor {
	
	private static final long serialVersionUID = -5874028189115497448L;
	protected ArrayList<DataPoint> dataPair;

	/**
	 * Get the evaluation standard R-square
	 * @return
	 * @throws Exception 
	 */
	public double getRsquare() throws Exception {
		float sumY = 0;	
		double deltaTrueY = 0;
		double deltaBarY = 0;
		double Rsquare;
		for ( DataPoint dataPoint:dataPair ) {		
			sumY += dataPoint.y;
		}
		float EY = sumY/dataPair.size();
		ToolsUtil.devPrintString("y-:"+EY);	
		for ( DataPoint dataPoint:dataPair ) {		
			ToolsUtil.devPrintString("xi:"+dataPoint.x+"  "+
					"yi:"+dataPoint.y+"  "+
					"yi^:"+getPredict(dataPoint.x));
			deltaTrueY += Math.pow( (dataPoint.y-getPredict(dataPoint.x)), 2);
			deltaBarY += ( dataPoint.y - EY ) * ( dataPoint.y - EY );
		}
		Rsquare = 1 - deltaTrueY / deltaBarY;
		Rsquare = (double)Math.round(Rsquare*10000)/10000;
		return Rsquare;
	}

	public abstract void generateCoefficients(int maxPow) throws Exception;

	public abstract double[] getCoefficients() throws Exception;

	public abstract double getPredict(double x) throws Exception;

}