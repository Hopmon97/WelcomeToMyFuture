package com.example.welcometomyfuture;

import static com.example.welcometomyfuture.background.type;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListActivity extends AppCompatActivity {

    String urladdress="http://"+MainActivity.ip +"/Android/customer.php";//rwta stelioooo
    String id;
    String[] customer;
    String[] user;
    String[] name;
    String[] surname;
    String[] email;
    String[] address;
    String[] city;
    String[] country;
    String[] postalcode;
    String[] phone;
    String[] typelist;




    ListView listView;
    BufferedInputStream is;
    String line= null;
    String result = null;

    BottomNavigationView bottom_navigation;
    BottomNavigationView bottom_navigation2;
    BottomNavigationView bottom_navigation3;
    BottomNavigationView bottom_navigation4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView= findViewById(R.id.lview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        ListViewAdapter customLiseView=new ListViewAdapter(this, customer,user,name,surname, email, address, city, country, postalcode, phone, typelist);
        listView.setAdapter(customLiseView);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation2 = findViewById(R.id.bottom_navigation2);
        bottom_navigation3 = findViewById(R.id.bottom_navigation3);
        bottom_navigation4 = findViewById(R.id.bottom_navigation4);


        if (type.equals("0")) {

            bottom_navigation.setVisibility(View.VISIBLE);
            bottom_navigation2.setVisibility(View.GONE);
            bottom_navigation3.setVisibility(View.GONE);
            bottom_navigation4.setVisibility(View.GONE);
            bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), admin.class));
                            return true;
                        case R.id.nav_Users:
                            startActivity(new Intent(getApplicationContext(), ListActivity.class));
                            return true;
                        case R.id.nav_products:
                            startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                            return true;
                        case R.id.nav_cart:
                            startActivity(new Intent(getApplicationContext(), Cart.class));
                            return true;
                        case R.id.nav_order:
                            startActivity(new Intent(getApplicationContext(),OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });
        }
        else if(type.equals("1"))
        {
            bottom_navigation2.setVisibility(View.VISIBLE);

            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation3.setVisibility(View.GONE);
            bottom_navigation4.setVisibility(View.GONE);



            bottom_navigation2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), Geoponos.class));
                            return true;
                        case R.id.nav_products:
                            startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                            return true;
                        case R.id.nav_cart:
                            startActivity(new Intent(getApplicationContext(), Cart.class));
                            return true;
                        case R.id.nav_order:
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });


        }
        else if(type.equals("2"))
        {
            bottom_navigation3 = findViewById(R.id.bottom_navigation3);
            bottom_navigation3.setVisibility(View.VISIBLE);
            bottom_navigation2.setVisibility(View.GONE);
            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation4.setVisibility(View.GONE);

            bottom_navigation3.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), Georgosss.class));
                            return true;
                        case R.id.nav_Users:
                            startActivity(new Intent(getApplicationContext(), ListActivity.class));
                            return true;
                        case R.id.nav_cart:
                            startActivity(new Intent(getApplicationContext(), Cart.class));
                            return true;
                        case R.id.nav_order:
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });


        }
        else if(type.equals("3"))
        {
            bottom_navigation4 = findViewById(R.id.bottom_navigation4);
            bottom_navigation4.setVisibility(View.VISIBLE);
            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation2.setVisibility(View.GONE);
            bottom_navigation3.setVisibility(View.GONE);

            bottom_navigation4.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), SellerActivity.class));
                            return true;
                        case R.id.nav_preg:
                            startActivity(new Intent(getApplicationContext(), productsRegister.class));
                            return true;
                        case R.id.nav_products:
                            startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                            return true;
                        case R.id.nav_cart:
                            startActivity(new Intent(getApplicationContext(), Cart.class));
                            return true;
                        case R.id.nav_order:
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });


        }

    }

    private void collectData()
    {//connection
        try
        {
            URL url = new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        // content
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();

        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //JSON
        try
        {
            JSONArray ja = new JSONArray(result);
            JSONObject jo=null;
            customer = new String[ja.length()];
            user = new String[ja.length()];
            name = new String[ja.length()];
            surname = new String[ja.length()];
            email = new String[ja.length()];
            address = new String[ja.length()];
            city = new String[ja.length()];
            country = new String[ja.length()];
            postalcode = new String[ja.length()];
            phone = new String[ja.length()];
            typelist = new String[ja.length()];

           // imagepath = new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                customer[i]=jo.getString("customerID");
                user[i]=jo.getString("userName");
                name[i]=jo.getString("customerName");
                surname[i]=jo.getString("customerSurname");
                email[i]=jo.getString("email");
                address[i]=jo.getString("address");
                city[i]=jo.getString("city");
                country[i]=jo.getString("country");
                postalcode[i]=jo.getString("postalcode");
                phone[i]=jo.getString("phone");
                typelist[i]=jo.getString("type");


                //imagepath[i]=jo.getString("photo");

            }



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}