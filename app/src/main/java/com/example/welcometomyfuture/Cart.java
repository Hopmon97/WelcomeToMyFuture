package com.example.welcometomyfuture;

import static com.example.welcometomyfuture.background.type;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Cart extends AppCompatActivity {

    TextView tvTotal;

    String urladdress="http://"+MainActivity.ip +"/Android/get_cart_for_user.php";
    String[] pname;
    String[] price;
    String[] quantity;
    String[] pID;

    ListView listView;
    BufferedInputStream is;
    String line= null;
    String result = null;

    Double total_price = 0.0;

    BottomNavigationView bottom_navigation;
    BottomNavigationView bottom_navigation5;
    BottomNavigationView bottom_navigation3;
    BottomNavigationView bottom_navigation4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        listView = findViewById(R.id.recview);
        tvTotal = findViewById(R.id.tvTotal);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        GetCart getCart = new GetCart(Cart.this);
        getCart.execute();

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation5 = findViewById(R.id.bottom_navigation5);
        bottom_navigation3 = findViewById(R.id.bottom_navigation3);
        bottom_navigation4 = findViewById(R.id.bottom_navigation4);

        if (type.equals("0")) {

            bottom_navigation.setVisibility(View.VISIBLE);
            bottom_navigation5.setVisibility(View.GONE);
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
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });
        } else if (type.equals("1")) {
            bottom_navigation5.setVisibility(View.VISIBLE);

            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation3.setVisibility(View.GONE);
            bottom_navigation4.setVisibility(View.GONE);


            bottom_navigation5.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(getApplicationContext(), Georgosss.class));
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


        } else if (type.equals("2")) {
            bottom_navigation3 = findViewById(R.id.bottom_navigation3);
            bottom_navigation3.setVisibility(View.VISIBLE);
            bottom_navigation5.setVisibility(View.GONE);
            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation4.setVisibility(View.GONE);

            bottom_navigation3.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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


        } else if (type.equals("3")) {
            bottom_navigation4.setVisibility(View.VISIBLE);
            bottom_navigation.setVisibility(View.GONE);
            bottom_navigation5.setVisibility(View.GONE);
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
        } }

    public class GetCart extends AsyncTask<String,Void,String> {

        Context context;

        public GetCart(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(String result) {

            System.out.println(result);

            try
            {
                JSONArray ja = new JSONArray(result);
                JSONObject jo=null;
                pID = new String[ja.length()];
                pname = new String[ja.length()];
                price = new String[ja.length()];
                quantity = new String[ja.length()];

                // imagepath = new String[ja.length()];

                for(int i=0;i<ja.length();i++)
                {
                    jo=ja.getJSONObject(i);
                    pID[i] = jo.getString("productID");
                    pname[i]=jo.getString("productName");
                    quantity[i]=jo.getString("quantity");
                    price[i]=jo.getString("price");

                    total_price+= Double.valueOf(price[i]);

                    //imagepath[i]=jo.getString("photo");
                }

                tvTotal.setText("Total Amount: " + total_price);

                CartAdabter customLiseView=new CartAdabter(Cart.this, pname,price, quantity);
                listView.setAdapter(customLiseView);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                Toast.makeText(Cart.this, "No products in Cart", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(String... voids) {

            String result = "";

            String connstr = urladdress;

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("customerID", "UTF-8") + "=" + URLEncoder.encode(background.customerID, "UTF-8");

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


    public void checkOut(View view)
    {
        Bundle bundle = new Bundle();

        bundle.putString("total", String.valueOf(total_price));

        Intent intent = new Intent(Cart.this, CheckOut.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}