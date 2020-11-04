package src.com.wp.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClient2 {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8188/");
        CloseableHttpResponse execute = build.execute(httpGet);

        HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity));
    }
}
