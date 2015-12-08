package com.jsptpd.jdm.tools.regression;

import java.util.ArrayList;

import Jama.Matrix;

import com.jsptpd.jdm.tools.ToolsUtil;
import com.jsptpd.jdm.vo.DataPoint;

public class MultiRegression extends AbstractRegression {

	private static final long serialVersionUID = -4958793056477041485L;
	private ArrayList<Double> vectorX;
	private ArrayList<Double> vectorY;
	
	/** coefficients */
	private double[] coefficient;
	
	/**
	 * Constructor
	 * @param oriData
	 */
	public MultiRegression(ArrayList<DataPoint> oriData) {
		dataPair = oriData;
		vectorX = new ArrayList<Double>();
		vectorY = new ArrayList<Double>();
		for ( int i=0; i<dataPair.size(); i++ ) {
			vectorX.add(dataPair.get(i).x);
			vectorY.add(dataPair.get(i).y);
		}
	}
	
	/**
	 * 最小二乘法求取多项式回归的系数
	 * @param num
	 * @return
	 */
	@Override
	public void generateCoefficients(int maxPow) {
		Matrix phiT=getVandermondeMatrix(vectorX, maxPow);
		ToolsUtil.devPrintMatrix("phiT",phiT);
		Matrix phi=phiT.transpose();
		double[][] valsY = new double[vectorY.size()][1];
		for( int m=0; m<vectorX.size(); m++ )
			valsY[m][0]=vectorY.get(m);
		Matrix matrixY=new Matrix(valsY);
		Matrix left=phi.times(phiT);
		Matrix right=phi.times(matrixY);
		ToolsUtil.devPrintMatrix("left",left);
		ToolsUtil.devPrintMatrix("right",right);
		try {
			Matrix coefMatrix=left.solve(right);
			coefficient = new double[maxPow+1];
			for(int i=0; i<=maxPow; i++) {
				coefficient[i] = coefMatrix.get(i, 0);
			}
			for(int i=0;i<coefficient.length;i++) {
				ToolsUtil.devPrintString("coefficient:"+coefficient[i]);
			}
			ToolsUtil.devPrintMatrix("coefMatrix",coefMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取系数
	 * @param num
	 * @return
	 */
	@Override
	public double[] getCoefficients() {
		if(null == coefficient) {
			throw new IllegalArgumentException(
					"Please execute the generateCoefficients function first!");
		}
		else
			return coefficient;
	}
	
	/**
	 * 根据传入的x预估y值
	 * @param x
	 * @return
	 */
	@Override
	public double getPredict(double x) throws Exception {
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
	 * 求取范德蒙矩阵
	 * @param vectorX
	 * @param column
	 * @return
	 */
	public Matrix getVandermondeMatrix(ArrayList<Double> vectorX, int column) {
		double[][] vals = new double[vectorX.size()][column+1];
		for( int m=0; m<vectorX.size(); m++ )
			vals[m][0] = 1;
		for( int m=0; m<vectorX.size(); m++ )
			for( int n=1; n<=column; n++ )
				vals[m][n] = Math.pow(vectorX.get(m),n);
		Matrix vandermonde = new Matrix(vals);
		return vandermonde;	
	}
}