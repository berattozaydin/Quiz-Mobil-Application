package com.example.quizmobilapp;


import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultSend extends AppCompatActivity {
    public void jsonDataSend(int totalQuestionNumber,int score,String data){
        LocalStorage localStorage;
        String url = getString(R.string.api_server)+"/quiz"+"/"+data+"/sonucs";
        JSONObject params= new JSONObject();
        try {
            params.put("slug",data);
            params.put("dogrusayisi",score);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String yolla = params.toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http= new Http(ResultSend.this,url);
                http.setMethod("POST");
                http.setData(yolla);
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            Toast.makeText(ResultSend.this,"Error"+code,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ResultSend.this,"Error"+code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}
