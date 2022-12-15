package com.example.welcometomyfuture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    EditText pas, usr,typ;

    public static Context context;

    public static String ip="192.168.0.12";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        usr = findViewById(R.id.etUser);
        pas = findViewById(R.id.etPassword);




        }


    public void loginBtn(View view){
        String user= usr.getText().toString();
        String pass= pas.getText().toString();

    background bg = new background(this);
    bg.execute(user,pass);
    }

    public void registerBtn(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();
    }
}