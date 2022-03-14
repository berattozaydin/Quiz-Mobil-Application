package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    TextView tv_question,questionsay;
    Button a,b,c,d;
    Random random;
    int currentpost;
    int score=0;
    int questionsayisi=0,totalQuestionNumber=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        tv_question=findViewById(R.id.tv_Question);
        questionsay=findViewById(R.id.questionSayi);
        a=findViewById(R.id.btn_a);
        b=findViewById(R.id.btn_b);
        c=findViewById(R.id.btn_c);
        d=findViewById(R.id.btn_d);
        random = new Random();
        String data;
        Intent intent = getIntent();
        data=intent.getStringExtra("data");
        String url =  getString(R.string.api_server)+"/quiz"+"/"+data;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http= new Http(QuestionActivity.this,url);
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                JSONArray jsonArry = response.getJSONArray("sorulars");
                              //  ListView lv = (ListView) findViewById(R.id.listView2);
                                ArrayList<HashMap<String,String>> array = new ArrayList<>();
                                for(int i=0;i<jsonArry.length();i++){
                                    HashMap<String,String> quest = new HashMap<>();
                                    JSONObject obj = jsonArry.getJSONObject(i);
                                    quest.put("question",obj.getString("question"));
                                    quest.put("a",obj.getString("a"));
                                    quest.put("b",obj.getString("b"));
                                    quest.put("c",obj.getString("c"));
                                    quest.put("d",obj.getString("d"));
                                    quest.put("correctanswer",obj.getString("correctanswer"));
                                   totalQuestionNumber++;
                                   array.add(quest);

                                }
                                /*ListAdapter adapter = new SimpleAdapter(QuestionActivity.this, array, R.layout.activity_list_row2,new String[]{"question","a","b","c","d"}, new int[]{R.id.question,R.id.a, R.id.b,R.id.c,R.id.d});
                                lv.setAdapter(adapter);*/
                              /*  rgroup = (RadioGroup) findViewById(R.id.rGroup);
                                a=(RadioButton)findViewById(R.id.a);
                                b=(RadioButton)findViewById(R.id.b);
                                c=(RadioButton)findViewById(R.id.c);
                                d=(RadioButton)findViewById(R.id.d);
if(a.isChecked()){
   int radioid = rgroup.getCheckedRadioButtonId();
}*/
                                JSONObject params = new JSONObject();
                                try{
                                    params.put("slug",data);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                String data = params.toString();
                                http.setMethod("POST");
                                http.setData(data);
                                http.send();
                               // currentpost= random.nextInt(array.size());
                                setdata(questionsayisi,array);
a.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (array.get(questionsayisi).get("correctanswer").trim().toLowerCase().equals(a.getTag().toString().trim().toLowerCase())) {
            score++;
        }
        if(totalQuestionNumber==questionsayisi+1){
        Toast.makeText(getApplicationContext(),"Score" + score,Toast.LENGTH_LONG).show();
        }else{
        questionsayisi++;
        setdata(questionsayisi,array);
        }
    }
});
b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view){
            if (array.get(questionsayisi).get("correctanswer").trim().toLowerCase().equals(b.getTag().toString().trim().toLowerCase())) {
                score++;
            }
        if(totalQuestionNumber==questionsayisi+1){
            Toast.makeText(getApplicationContext(),"Score" + score,Toast.LENGTH_LONG).show();
        }else{
            questionsayisi++;
            setdata(questionsayisi,array);
        }
    }
});
c.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (array.get(questionsayisi).get("correctanswer").trim().toLowerCase().equals(c.getTag().toString().trim().toLowerCase())) {
            score++;
        }
        if(totalQuestionNumber==questionsayisi+1){
            Toast.makeText(getApplicationContext(),"Score" + score,Toast.LENGTH_LONG).show();
        }else{
            questionsayisi++;
            setdata(questionsayisi,array);
        }
    }
});
d.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (array.get(currentpost).get("correctanswer").trim().toLowerCase().equals(d.getTag().toString().trim().toLowerCase())) {
            score++;
        }
        if(totalQuestionNumber==questionsayisi+1){
            Toast.makeText(getApplicationContext(),"Score" + score,Toast.LENGTH_LONG).show();

        }else{
            questionsayisi++;
            setdata(questionsayisi,array);
        }
    }
});
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
            }}).start();
}

    private void setdata(int currentpost,ArrayList<HashMap<String,String>> array) {
            questionsay.setText("sayi " + questionsayisi);
            tv_question.setText(array.get(currentpost).get("question").toString());
            a.setText(array.get(currentpost).get("a").toString());
            b.setText(array.get(currentpost).get("b").toString());
            c.setText(array.get(currentpost).get("c").toString());
            d.setText(array.get(currentpost).get("d").toString());

    }

}