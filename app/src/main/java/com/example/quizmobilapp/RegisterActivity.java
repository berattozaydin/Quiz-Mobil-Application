package com.example.quizmobilapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
EditText etName,etEmail,etPassword,etPasswordConf;
Button btn_Reg;
String name,email,password,passwordconf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=findViewById(R.id.et_Name);
        etEmail=findViewById(R.id.et_RegEmailConf);
        etPassword=findViewById(R.id.et_RegPasswordConf);
        etPasswordConf=findViewById(R.id.et_RegPasswordConformation);
btn_Reg=findViewById(R.id.btn_RegisterConf);
btn_Reg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        controlreg();
    }
});
    }

    private void controlreg() {
        name=etName.getText().toString();
        email=etEmail.getText().toString();
        password=etPassword.getText().toString();
        passwordconf=etPasswordConf.getText().toString();
        if(name.isEmpty() && email.isEmpty() && passwordconf.isEmpty() && password.isEmpty())
            AlertFail("Alanların Doldurulması Zorunludur");
        else
            sendRegister();

    }

    private void sendRegister() {
        JSONObject params = new JSONObject();
        try{
            params.put("name",name);
            params.put("email",email);
            params.put("password",password);
            params.put("password_confirmation",passwordconf);

        }catch (JSONException e){
            e.printStackTrace();

        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/register";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(RegisterActivity.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code==201 || code == 200)
                           writescreen("Kayıt Başarılı");
                        else if(code == 422){
                        try {
                            JSONObject response = new JSONObject(http.getResponse());
                            String msg = response.getString("Message");
                            AlertFail(msg);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }else{
                            Toast.makeText(RegisterActivity.this,"Error"+code,Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        }).start();
    }
    private void writescreen(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Başarılı")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage(s)
                .setPositiveButton("Login",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }).show();
    }

    private void AlertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Hata")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage(s)
                .setPositiveButton("ok",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}