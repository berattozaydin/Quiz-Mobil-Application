package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
TextView tv = findViewById(R.id.question);
String data;
Intent intent = getIntent();
data=intent.getStringExtra("data");
//tv.setText(data);
        String url =  getString(R.string.api_server)+"/quiz"+"/"+data;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http= new Http(QuestionActivity.this,url);
                //http.setMethod("GET");
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String name=response.getString("sorulars");
                                tv.setText(name);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
            }
        }).start();


}
}