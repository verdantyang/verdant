package common;

import com.verdant.jtools.common.web.utils.http.HttpClientUtils2;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

/**
 * Created by Administrator on 2016/4/20.
 */
public class HttpClientUtils2Test {
    @Test
    public void conn() throws Exception {
        Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
        String result = HttpClientUtils2.postPlain("https://open.weixin.qq.com/",
                new StringEntity("{}", ContentType.APPLICATION_JSON), null);
//        String result = EntityUtils.toString(entity);
//        InputStream is = entity.getContent();
        System.out.println(result);
        System.out.println(HttpClientUtils2.postPlain("https://kyfw.12306.cn/otn/leftTicket/init",
                new StringEntity("{}", ContentType.APPLICATION_JSON), null));
    }
}
