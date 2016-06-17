package common;

import com.verdant.jtools.common.utils.base.DateUtils2;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

/**
 * DateUtils2测试用例
 *
 * @author verdant
 * @since 2016/06/15
 */
public class DateUtils2Test {
    @Test
    public void usage() {
        String sdf = "yyyy-MM-dd HH:mm:ss";
        String nowStr = DateUtils2.getNowTime(sdf);
        Date nowDate = DateUtils2.turnStringToDate(nowStr, sdf);
        String transDateStr = DateUtils2.turnDateToString(nowDate, sdf);
        Assert.assertEquals(nowStr, transDateStr);
        System.out.println(nowStr);
        Date now= DateUtils2.turnTimestampToDate(System.currentTimeMillis());
        System.out.println(now);
        String formatMS = DateUtils2.timeConversion(312313000);
        System.out.println(formatMS);
    }
}
