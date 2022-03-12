package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
TextView tv;
List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tv=findViewById(R.id.tv_Quiz);
        String url = getString(R.string.api_server)+"/liste";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http= new Http(QuizActivity.this,url);
                //http.setMethod("GET");
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                   JSONArray jsonArray = jsonObject.getJSONArray("data");
                                   for(int i=0;i<jsonArray.length();i++){
                                       JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                       String title=jsonObject1.getString("title");
                                       list.add(title);
                                   }
                                   for(String liste:list){
                                       tv.setText(liste);
                                   }


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                    }
                });
            }
        }).start();
    }
}