package cse.knu.kr.deagu.beaconsecurityapp;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cse.knu.kr.deagu.beaconsecurityapp.Users.UserInfo;

/**
 * Created by juhee on 2016-07-25.
 * 회원가입
 */
public class SignupActivity extends Activity {
    EditText editId;
    EditText editpw;
    Button btnSign;
    String macId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("start", "start");

        editId = (EditText) findViewById(R.id.edit_id);
        editpw = (EditText) findViewById(R.id.edit_pw);
        macId = getMcAdr();
        editId.setText(macId);
        editId.setFocusable(false);
        editId.setClickable(false);

    }

    private String getMcAdr() {

        WifiManager mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();

        return mWifiInfo.getMacAddress();
    }

    public void onClick(View view) {
        String id = editId.getText().toString();
        String pw = editpw.getText().toString();

        RequestParams params = new RequestParams();

        switch (view.getId()) {
            case R.id.btn_sign:
                new Signup(new UserInfo(id, pw)).execute();

                break;
        }

    }

    private class Signup extends AsyncTask<Void, Void, Void> {
        private final String TAG = "Signup";

        private UserInfo userInfo;
        private String result;

        /*
   `    회원가입
        */

        public Signup(UserInfo userInfo) {
            this.userInfo = userInfo;
        }


        //doInBackground 후에 실행되는 메소드
        //ok인지 아닌지도 얘가 판단해야함!

        @Override
        protected Void doInBackground(Void... params) {
            String urlString = Static.baseURL + "users";

            HttpURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;
            try {
                url = new URL(urlString);

                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500 * 1000);
                conn.setReadTimeout(200 * 1000);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Gson gson = new Gson();
                JSONObject job = new JSONObject(gson.toJson(userInfo));
                Log.d(TAG, job.toString());
                os = conn.getOutputStream();
                os.write(job.toString().getBytes());
                os.flush();

                //서버에서 결과 받아오기

                int responseCode = conn.getResponseCode();

                switch (responseCode) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        /*
                        회원가입 결과 가져오기
                         */
                        result = sb.toString();
                        Log.d(TAG, result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
