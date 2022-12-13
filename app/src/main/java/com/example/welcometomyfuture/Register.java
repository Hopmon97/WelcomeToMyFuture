package com.example.welcometomyfuture;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Register extends AppCompatActivity {
    private EditText etUser,etUsername,etSurname,etPassword,etRepeatPassword,etEmailAddress,etAddress,etCity,etCountry,etPostalCode,etPhone;
    private RadioGroup rdEpilogi;
   // private Button btnRegister;
    private String user,name,surname,password,repeatPassword,email,address,city,country,postal,phone,type;
    //private int selection;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUser=findViewById(R.id.etUser);
        etUsername= findViewById(R.id.etUsername);
        etSurname= findViewById(R.id.etSurname);
        etPassword= findViewById(R.id.etPassword);
        etRepeatPassword= findViewById(R.id.etRepeatPassword);
        etEmailAddress=findViewById(R.id.etEmailAddress);
        etAddress= findViewById(R.id.etAddress);
        etCity= findViewById(R.id.etCity);
        etCountry= findViewById(R.id.etCountry);
        etPostalCode= findViewById(R.id.etPostalCode);
        etPhone= findViewById(R.id.etPhone);
        rdEpilogi = findViewById(R.id.rdEpilogi);

    }
    public void save(View view)
    {   user= etUser.getText().toString().trim();
        name= etUsername.getText().toString().trim();
        surname= etSurname.getText().toString().trim();
        password= etPassword.getText().toString().trim();
        repeatPassword=etRepeatPassword.getText().toString().trim();
        email=etEmailAddress.getText().toString().trim();
        address=etAddress.getText().toString().trim();
        city=etCity.getText().toString().trim();
        country=etCountry.getText().toString().trim();
        postal=etPostalCode.getText().toString().trim();
        phone=etPhone.getText().toString().trim();


        if(!password.equals(repeatPassword))
        {
            Toast.makeText(this,"Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(name.equals("")&& email.equals("") && password.equals(""))
        {
            Toast.makeText(this,"Please fill all spaces", Toast.LENGTH_SHORT).show();

        }
        else
        {
            RegisterUser registerUser = new RegisterUser(this);
            registerUser.execute(user,name,surname,password,email,address,city,country,postal,phone,type);

        }



    }

    public class RegisterUser extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        RegisterUser(Context ctx)
        {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String user = params[0];
                String name = params[1];
                String surname= params[2];
                String password = params[3];
                String email = params[4];
                String address  = params[5];
                String city = params[6];
                String country = params[7];
                String postal = params[8];
                String phone = params[9];
                String type = params[10];


                URL url = new URL("http://"+MainActivity.ip+"/Android/Registeration.php");



                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"

                +URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(surname,"UTF-8")+"&"

                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"

                +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"

                +URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"

                +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"

                +URLEncoder.encode("country","UTF-8")+"="+URLEncoder.encode(country,"UTF-8")+"&"

                +URLEncoder.encode("postal","UTF-8")+"="+URLEncoder.encode(postal,"UTF-8")+"&"

                +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"

                +URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8");



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
            alertDialog.setTitle("Register Status:");
            alertDialog.setMessage(result);
            alertDialog.show();
        }

        }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbArgo:
                if (checked)
                    type="1";

            case R.id.rbFarmer:
                if (checked)
                   type="2";

            case R.id.rbSeller:
                if (checked)
                    type="3";

        }
    }
}