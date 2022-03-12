package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    String email,password;
    EditText et_Email,et_Password;
    Button btn_Login,btn_Reg;
    LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        localStorage = new LocalStorage(LoginActivity.this);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        btn_Login=findViewById(R.id.btn_Login);
        btn_Reg=findViewById(R.id.btn_RegisterScreen);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controllogin();
            }
        });
        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void controllogin() {
        email=et_Email.getText().toString();
        password=et_Password.getText().toString();
        if(email.isEmpty() && password.isEmpty())
            alertFail("Email ve Şifre Boş Olamaz");
        else
            sendLogin();
    }

    private void sendLogin() {
        JSONObject params= new JSONObject();
        try {
            params.put("email",email);
            params.put("password",password);

        }catch (JSONException e){
            e.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/login";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(LoginActivity.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                String token = response.getString("token");
                                localStorage.setToken(token);
                               Intent i = new Intent(LoginActivity.this,UserActivity.class);
                               startActivity(i);
                                finish();
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else if(code ==422){
                            try{
                               JSONObject response = new JSONObject(http.getResponse());
                               String msg = response.getString("message");
                               alertFail(msg);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else if(code == 401){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Error"+code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Fail")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage(s)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}