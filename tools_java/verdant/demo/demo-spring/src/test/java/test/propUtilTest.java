package test;

import com.verdant.jtools.spring.utils.PropUtils;
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
        PropUtils.getPattern("mysql.*");
        System.out.println(PropUtils.getPattern("mysql.*"));
    }
}
