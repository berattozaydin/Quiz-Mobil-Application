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

public class LoginActivity extends AppCompatActivity {
    String email,password;
    EditText et_Email,et_Password;
    Button btn_Login,btn_Reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        Intent i=new Intent(this,UserActivity.class);
        startActivity(i);
        finish();
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