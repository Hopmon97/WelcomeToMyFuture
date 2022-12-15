package com.example.welcometomyfuture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ForgotPassword extends AppCompatActivity {

    private EditText tvOld,tvNew,tvRepeat;
    private String oldp,newp,repeatp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvNew=findViewById(R.id.tvNew);
        tvOld= findViewById(R.id.tvOld);
        tvRepeat= findViewById(R.id.tvRepeat);

    }
    public void changePassword(View view)
    {
        oldp=tvOld.getText().toString().trim();
        newp=tvNew.getText().toString().trim();
        repeatp=tvRepeat.getText().toString().trim();


        if(!newp.equals(repeatp))
        {
            Toast.makeText(this,"Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(oldp.equals("")|| newp.equals("") || repeatp.equals(""))
        {
            Toast.makeText(this,"Please fill all spaces", Toast.LENGTH_SHORT).show();

        }
        else if(!oldp.equals(background.password))
        {
            Toast.makeText(this,"Old Password Mismatch", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Forgot forgot = new Forgot(this);
            forgot.execute(newp,background.customerID);

            Intent intent = new Intent (ForgotPassword.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public class Forgot extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        Forgot(Context ctx)
        {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String password = params[0];
                String customerID = params[1];



                URL url = new URL("http://"+MainActivity.ip+"/Android/forgot_password.php");



                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("customerID","UTF-8")+"="+URLEncoder.encode(customerID,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result="" ;
                String line="";
                while((line = bufferedReader.readLine())!=null )
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        //on post execution of the worker, we print the message that we got from the php script
        @Override
        protected void onPostExecute(String result)
        {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Password Status:");
            alertDialog.setMessage(result);
            alertDialog.show();
        }



}
}