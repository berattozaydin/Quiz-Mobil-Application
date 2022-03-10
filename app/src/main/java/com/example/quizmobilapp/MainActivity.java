package com.example.quizmobilapp;

import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {




    String urli="http://10.0.2.2/api/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        Button btnsend=findViewById(R.id.btn_Send);
       final String name;
        final String price;
      btnsend.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              String name;
              String price;

              JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urli, null,
                      new Response.Listener<JSONObject>()
                      {
                          @Override
                          public void onResponse(JSONObject response) {
                              try{
                                 Log.d("A",response.getString("name"));
                                  Log.d("B",response.getString("price"));

                              }catch (Exception e){
                                  Log.e("e", "onResponse: "+e);
                              }
                          }
                      },
                      new Response.ErrorListener()
                      {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Log.d("Error.Response", String.valueOf(error));
                          }
                      }
              );
              queue.add(getRequest);
          } });
}
    }
