package com.example.greasowang.missiona;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.*;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        EditText acc = (EditText) findViewById(R.id.et1);
        EditText pw = (EditText) findViewById(R.id.et2);
        acc.setText("0920235490");
        pw.setText("1234");
        Button loginButton = (Button) findViewById(R.id.btn1);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });
    }

    public void loginAction() {

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String apiUser = "iosCleaner";
        String apiKey = "mVVCQ";
        String checkCode = apiUser + "&" + apiKey + "&" + ts;
        checkCode = convertPassMd5(checkCode);
        EditText acc = (EditText) findViewById(R.id.et1);
        EditText pw = (EditText) findViewById(R.id.et2);
        String phone = acc.getText().toString();
        String password = pw.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("user", apiUser);
        rp.add("time", ts);
        rp.add("checkCode", checkCode);
        rp.add("phone", phone);
        rp.add("password", password);

        String url = "api/cleanerapp/login";
        HttpUtils.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String result = response.toString();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
                    String returnCode = jsonObject.get("return_code").getAsString();
                    if (returnCode != null && returnCode.equals("0")) {
                        Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, RankListActivity.class);
                        startActivity(intent);
                    } else {
                        String msg = jsonObject.get("msg").getAsString();
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Toast.makeText(LoginActivity.this, "連線發生錯誤，請再試一次", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
}
