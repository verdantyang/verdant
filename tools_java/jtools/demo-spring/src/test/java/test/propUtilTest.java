package test;

import com.jtools.spring.utils.PropUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by verdant on 2016/1/21.
 */
public class propUtilTest {
    @Before
    public void init(){

    }
    @Test
    public void testProp() {
        PropUtil.getPattern("mysql.*");
        System.out.println(PropUtil.getPattern("mysql.*"));
    }
}
