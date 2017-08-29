package com.verdant.dm.utils;

import Jama.Matrix;
import com.verdant.dm.common.Constants;

public class DebugLog {
    /**
     * 供调试时输出
     *
     * @param s
     */
    public static void devPrintString(String s) {
        if (1 == Constants.DevMode)
            System.out.println(s);
        else
            return;
    }

    public static void devPrintMatrix(String mName, Matrix m) {
        DebugLog.devPrintString(mName + ":");
        if (1 == Constants.DevMode)
            m.print(2, 4);
    }
}
