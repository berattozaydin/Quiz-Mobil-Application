package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
TextView tvName,tvEmail;
Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tvName=findViewById(R.id.et_StudentName);
        tvEmail=findViewById(R.id.et_StudentEmail);
        logOut=findViewById(R.id.btn_Logout);
        getuser();
logOut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        logout();
    }
});
    }

    private void logout() {
        Intent i =new Intent(UserActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void getuser() {
        tvName.setText("Ad Soyad - ");
        tvEmail.setText("Email - ");
    }
}