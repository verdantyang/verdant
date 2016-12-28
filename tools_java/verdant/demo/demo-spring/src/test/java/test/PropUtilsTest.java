package test;

import com.verdant.jtools.common.spring.utils.SpringPropUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by verdant on 2016/1/21.
 */
public class PropUtilsTest {
    @Before
    public void init(){

    }
    @Test
    public void testProp() {
        SpringPropUtils.getPattern("mysql.*");
        System.out.println(SpringPropUtils.getPattern("mysql.*"));
    }
}
