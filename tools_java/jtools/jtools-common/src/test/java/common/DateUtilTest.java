package common;

import com.jtools.common.utils.DateUtil;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

/**
 * Created by verdant on 2016/1/21.
 */
public class DateUtilTest {
    @Test
    public void usage() {
        String sdf = "yyyy-MM-dd HH:mm:ss";
        String nowStr = DateUtil.getNowTime(sdf);
        Date nowDate = DateUtil.turnStringToDate(nowStr, sdf);
        String transDateStr = DateUtil.turnDateToString(nowDate, sdf);
        Assert.assertEquals(nowStr, transDateStr);
        System.out.println(nowStr);
    }
}
