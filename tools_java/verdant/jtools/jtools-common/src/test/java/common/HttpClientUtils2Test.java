package common;

import com.verdant.jtools.common.web.http.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * HttpClientUtils2测试用例
 *
 * @author verdant
 * @since 2016/06/15
 */
public class HttpClientUtils2Test {
    @Test
    public void conn() throws Exception {
        Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
//        String result = HttpClientUtils.postPlain("https://open.weixin.qq.com/",
//                new StringEntity("{}", ContentType.APPLICATION_JSON), null);
//        String result = EntityUtils.toString(entity);
//        InputStream is = entity.getContent();
        String result = HttpClientUtils.postPlain("http://es.api.xinhuazhiyun.com//api/views/batch",
                new StringEntity("{\"token\":\"skdfKJHjkhgH345@$gyttTR\"}", ContentType.APPLICATION_JSON), null);
        System.out.println(result);
//        System.out.println(HttpClientUtils.postPlain("https://kyfw.12306.cn/otn/leftTicket/init",
//                new StringEntity("{}", ContentType.APPLICATION_JSON), null));
    }

//    private static final String url = "http://es.api.xinhuazhiyun.com/api/views/batch";
    private static final String url = "http://localhost:8080/api/views/batch";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static void main(String[] args) {
        String json = "{\"token\":\"skdfKJHjkhgH345@$gyttTR\"}";
        // 将JSON进行UTF-8编码,以便传输中文

        try {
            String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(encoderJson);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

