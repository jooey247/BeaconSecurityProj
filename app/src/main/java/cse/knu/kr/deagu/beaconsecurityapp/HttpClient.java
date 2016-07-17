package cse.knu.kr.deagu.beaconsecurityapp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by juhee on 2016-07-15.
 */
public class HttpClient {

    private static final String BASE_URL="http://123.130.48.180:5555/";
    //Singleton
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getInstance(){
        return HttpClient.client;
    }

    public static void get(String url,RequestParams params,AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url),params,responseHandler);
    }
    public static void post(String url,RequestParams params,AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url),params,responseHandler);
    }
    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL+relativeUrl;
    }
}
