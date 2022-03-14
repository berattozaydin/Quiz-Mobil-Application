package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {
    Button btn;
    RadioButton a,b,c,d;
    RadioGroup rgroup;
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
                               // String questions=response.getString("questions");
                                JSONArray jsonArry = response.getJSONArray("sorulars");
                                ListView lv = (ListView) findViewById(R.id.listView2);
                                ArrayList<HashMap<String, String>> array = new ArrayList<>();
                                for(int i=0;i<jsonArry.length();i++){
                                    HashMap<String,String> quest = new HashMap<>();
                                    JSONObject obj = jsonArry.getJSONObject(i);
                                    quest.put("question",obj.getString("question"));
                                    quest.put("a",obj.getString("a"));
                                    quest.put("b",obj.getString("b"));
                                    quest.put("c",obj.getString("c"));
                                    quest.put("d",obj.getString("d"));
                                    array.add(quest);
                                }
                                ListAdapter adapter = new SimpleAdapter(QuestionActivity.this, array, R.layout.activity_list_row2,new String[]{"question","a","b","c","d"}, new int[]{R.id.question,R.id.a, R.id.b,R.id.c,R.id.d});
                                lv.setAdapter(adapter);
                                rgroup = (RadioGroup) findViewById(R.id.rGroup);
                                a=(RadioButton)findViewById(R.id.a);
                                b=(RadioButton)findViewById(R.id.b);
                                c=(RadioButton)findViewById(R.id.c);
                                d=(RadioButton)findViewById(R.id.d);
if(a.isChecked()){
   int radioid = rgroup.getCheckedRadioButtonId();
}
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