package com.verdant.jdm.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Jama.Matrix;

import com.verdant.jdm.common.Constants;

public class ToolsUtil {
	/**
	 * 供调试时输出
	 * @param s
	 */
	public static void devPrintString(String s) {
		if( 1 == Constants.IsDev )
			System.out.println(s);		
		else
			return;
	}
	
	public static void devPrintMatrix(String mName,Matrix m) {
		ToolsUtil.devPrintString(mName+":");
		if( 1 == Constants.IsDev )
			m.print(2, 4);
	}
	
	public static int getTodayID() {
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(df.format(cal.getTime()));
	}
}
