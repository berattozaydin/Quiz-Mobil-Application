package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    TextView tv;
    String name, description;
    LocalStorage localStorage;
   // ListView listView;

    Data data;
    //ArrayList<HashMap> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static ArrayList<Data> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //listView = (ListView) findViewById(android.R.id.list);
        //tv = findViewById(R.id.quizid);
        String url =  getString(R.string.api_server)+"/liste";
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
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                ArrayList<HashMap<String, String>> array = new ArrayList<>();
                                ListView lv = (ListView) findViewById(R.id.listView1);
                                JSONArray jsonArry = response.getJSONArray("data");

                                for(int i=0;i<jsonArry.length();i++){
                                    HashMap<String,String> user = new HashMap<>();
                                    JSONObject obj = jsonArry.getJSONObject(i);
                                    user.put("title",obj.getString("title"));
                                    user.put("slug",obj.getString("slug"));
                                    user.put("description",obj.getString("description"));
                                    user.put("finished_at",obj.getString("finished_at"));
                                    array.add(user);
                                }
                                ListAdapter adapter = new SimpleAdapter(QuizActivity.this, array, R.layout.list_row,new String[]{"title","description","slug","finished_at"}, new int[]{R.id.title, R.id.description,R.id.slug, R.id.finished_at});
                                lv.setAdapter(adapter);
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                        Intent intent = new Intent(getApplicationContext(),QuestionActivity.class);
                                        intent.putExtra("data",array.get(i).get("slug").toString());
                                        startActivity(intent);
                                      // finish();


                                    }
                                });
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(QuizActivity.this,"Error"+code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    }
