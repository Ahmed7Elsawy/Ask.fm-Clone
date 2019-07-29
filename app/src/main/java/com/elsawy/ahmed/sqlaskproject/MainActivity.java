package com.elsawy.ahmed.sqlaskproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.elsawy.ahmed.sqlaskproject.Activities.SignUpActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.main_activity_textview);
        textView.setOnClickListener(even ->{startActivity(new Intent(this, SignUpActivity.class));});
    }
}
