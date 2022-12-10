package com.example.welcometomyfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
import java.text.BreakIterator;

public class Georgosss extends AppCompatActivity {
    String urladdress = "http://" + MainActivity.ip + "/Android/customer.php";//rwta stelioooo
    String id;
    String Name;
    String Surname;
    String Email;
    String Address;
    String City;
    String Country;
    String PostalCode;
    String Phone;
    String type;

    EditText etID, etName, etSurname,etEmail,etAddress,etCity,etCountry,etPostalCode,etPhone; //....;

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_georgosss);

        etID = findViewById(R.id.id);
        etName = findViewById(R.id.p_name);
        etSurname = findViewById(R.id.p_surname);
        etEmail = findViewById(R.id.p_email);
        etAddress = findViewById(R.id.p_address);
        etCity = findViewById(R.id.p_city);
        etCountry = findViewById(R.id.p_country);
        etPostalCode = findViewById(R.id.p_pcode);
        etPhone = findViewById(R.id.p_phone);



        etID.setText(background.customerID);
        etName.setText(background.customerName);
        etSurname.setText(background.customerSurname);
        etEmail.setText(background.email);
        etAddress.setText(background.address);
        etCity.setText(background.city);
        etCountry.setText(background.country);
        etPostalCode.setText(background.postalcode);
        etPhone.setText(background.phone);


        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Georgosss.class));
                        return true;
                    case R.id.nav_products:
                        startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        return true;
                }
                return false;
            }
        });
    }

    public void updateData(View view)
    {
        Name = etName.getText().toString();
        background.customerName = Name;
        Surname = etSurname.getText().toString();
        background.customerSurname = Surname;
        Email = etEmail.getText().toString();
        background.email = Email;
        Address = etAddress.getText().toString();
        background.address = Address;
        City = etCity.getText().toString();
        background.city = City;
        Country = etCountry.getText().toString();
        background.country = Country;
        PostalCode = etPostalCode.getText().toString();
        background.postalcode = PostalCode;
        Phone = etPhone.getText().toString();
        background.phone = Phone;







        UpdateUser update = new UpdateUser(Georgosss.this);
        update.execute();

    }

    public class UpdateUser extends AsyncTask<String,Void,String> {

        Context context;

        public UpdateUser(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                System.out.println(result);

                if (result.equals("success")) {
                    Intent intent = new Intent(Georgosss.this, Georgosss.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Georgosss.this, "Failed to update data", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... voids) {
            String result = "";

            String connstr = "http://" + MainActivity.ip + "/Android/update_data.php";

            //UPDATE customer set customerName = '$customerName', customerSurname= '$customerSurname'.... WHERE customerID = '$customerID'


            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(background.customerID, "UTF-8") + "&&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8") + "&&" +
                        URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(Surname, "UTF-8") + "&&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&&" +
                        URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8") + "&&" +
                        URLEncoder.encode("City", "UTF-8") + "=" + URLEncoder.encode(City, "UTF-8") + "&&" +
                        URLEncoder.encode("Country", "UTF-8") + "=" + URLEncoder.encode(Country, "UTF-8") + "&&" +
                        URLEncoder.encode("pcode", "UTF-8") + "=" + URLEncoder.encode(PostalCode, "UTF-8") + "&&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");

                //na peraseis oulles tes metavlites pou kamnei edit o xristis
//lol gt o writer itan se comments
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, StandardCharsets.ISO_8859_1));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;


            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }


            return result;
        }
    }
}

