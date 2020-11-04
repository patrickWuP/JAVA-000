package src.com.wp.http;

import okhttp3.*;

import java.io.IOException;

public class HttpClient3 {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8188/";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
        System.out.println(execute.body().string());
    }
}
