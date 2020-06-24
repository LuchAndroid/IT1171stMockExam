package com.example.it117firstmockexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button btnCheck, btnClose;
    private EditText txtPlate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initiate
        btnCheck = findViewById(R.id.btnCheck);
        btnClose = findViewById(R.id.btnClose);
        txtPlate = findViewById(R.id.txtPlate);
    }
}