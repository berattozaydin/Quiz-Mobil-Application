package com.example.quizmobilapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class question_row extends AppCompatActivity {
RadioButton a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_row2);
        a=(RadioButton)findViewById(R.id.a);
        b=(RadioButton)findViewById(R.id.b);
        c=(RadioButton)findViewById(R.id.c);
        d=(RadioButton)findViewById(R.id.d);
    }
}

