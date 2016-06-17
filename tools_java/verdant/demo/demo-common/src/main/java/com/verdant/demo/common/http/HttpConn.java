package com.verdant.demo.common.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/4/20.
 */
public class HttpConn {
    private final Integer TIMEOUT_CONN = 60;
    private final Integer TIMEOUT_READ = 60;
    private final String url = "http://localhost/api/manage/login";


    public void way1() {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
        try {
            entity.addPart("user_id", new StringBody("test@sh.com", Charset.forName("UTF-8")));
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
    }

    public void way2(String url) throws MalformedURLException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            //设置是否从httpUrlConnection读入，默认情况下是true
            connection.setDoInput(true);
            //设置是否向httpUrlConnection输出（默认情况false），post请求参数要放在http正文内
            connection.setDoOutput(true);

            connection.setConnectTimeout(TIMEOUT_CONN);
            connection.setReadTimeout(TIMEOUT_READ);

            connection.setRequestProperty("timestamp", System.currentTimeMillis() + "");

            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
