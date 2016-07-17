package cse.knu.kr.deagu.beaconsecurityapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

/**
 * Created by juhee on 2016-07-15.
 */
public class LoginActivity extends Activity {
    EditText editId;
    EditText editpw;
    Button btnSign;
    Button btnLogin;

    String macId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("start", "start");

        //get user's MacAdress
        //can not modify!
        macId=getMcAdr();
        editId.setText(macId);
        editId.setFocusable(false);
        editId.setClickable(false);

        AsyncHttpClient client = cse.knu.kr.deagu.beaconsecurityapp.HttpClient.getInstance();
        PersistentCookieStore myCookieStore=new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);

    }

    private String getMcAdr() {

        WifiManager mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();

        return mWifiInfo.getMacAddress();
    }

    public void onClick(View view) {
        editId=(EditText)findViewById(R.id.edit_id);
        editpw=(EditText)findViewById(R.id.edit_pw);

        String id=editId.getText().toString();
        String pw=editpw.getText().toString();

        RequestParams params = new RequestParams();

        switch (view.getId()){
            case R.id.btn_sign:
            //Users can use mainActivity after sign-in
                Log.i("msg","Clicked SignUp Btn id :"+editId.getText()+" pw :"+editpw.getText());
                params.put("id", id);
                params.put("pw", pw);
                cse.knu.kr.deagu.beaconsecurityapp.HttpClient.get("", params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //성공
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //실패
                    }
                });

                break;
            case R.id.btn_login:

                break;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        }
    }



