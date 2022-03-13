package com.example.quizmobilapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {
TextView tvName,tvEmail,tvid;
LocalStorage localStorage;
Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tvName=findViewById(R.id.et_StudentName);
        tvEmail=findViewById(R.id.et_StudentEmail);
        tvid=findViewById(R.id.et_Studentid);
        logOut=findViewById(R.id.btn_Logout);
        getuser();
logOut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        logout();
    }
});
    }



    private void getuser() {
       String url =  getString(R.string.api_server)+"/user";
       new Thread(new Runnable() {
           @Override
           public void run() {
               Http http= new Http(UserActivity.this,url);
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
                               String name=response.getString("name");
                               String email = response.getString("email");
                               String id = response.getString("id");
                               tvName.setText(name);
                               tvEmail.setText(email);
                               Intent i = new Intent(UserActivity.this,QuizActivity.class);
                               startActivity(i);
                               finish();
                           }catch(JSONException e){
                               e.printStackTrace();
                           }
                       }else{
                           Toast.makeText(UserActivity.this,"Error"+code,Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       }).start();
    }
   private void logout() {
        String url = getString(R.string.api_server)+"/logout";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http= new Http(UserActivity.this,url);
                http.setMethod("POST");
                http.setToken(true);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(UserActivity.this,"Error"+code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}