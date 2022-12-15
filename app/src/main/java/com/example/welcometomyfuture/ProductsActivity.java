package com.example.welcometomyfuture;

import static com.example.welcometomyfuture.background.type;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class ProductsActivity extends AppCompatActivity {
    String[] code;
    String[] productName;
    String[] productPrice;
    String[] productSeller;
    String[] productDescription;
    String[] image;
    String[] pquantity;
    String[] types;
    public EditText qua;


    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;


    BottomNavigationView bottom_navigation;
    BottomNavigationView bottom_navigation5;
    BottomNavigationView bottom_navigation3;
    BottomNavigationView bottom_navigation4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list_view );

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
        }


        listView= findViewById(R.id.llview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();

        //for(int i=0; i<productName.length; i++)
       // {
        //    System.out.println("i+ " + i + " " + productName[i]);

       // }
        ProductsListViewActivity customLiseView = new ProductsListViewActivity(this, code,productName, productPrice,productSeller,productDescription, image,pquantity,types);
        listView.setAdapter(customLiseView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle data = new Bundle();
                data.putString("ID", code[position]);
                data.putString("name", productName[position]);
                data.putString("description", productDescription[position]);
                data.putString("price", productPrice[position]);
                data.putString("seller", productSeller[position]);
                data.putString("type", types[position]);
                data.putString("image", image[position]);
                data.putString("totalq",pquantity[position]);


                Intent intent = new Intent(ProductsActivity.this, productDetails.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    private void collectData() {//connection


        try {
            if(background.type.equals("0")) {
            URL url = new URL("http://" + MainActivity.ip + "/Android/get_products.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                is = new BufferedInputStream(con.getInputStream());
        }
            else if(background.type.equals("1")) {
                URL url = new URL("http://" + MainActivity.ip + "/Android/get_products_farmer.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                is = new BufferedInputStream(con.getInputStream());
        }
            else if(background.type.equals("2")) {
            URL url = new URL("http://" + MainActivity.ip + "/Android/get_products_agronomist.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                is = new BufferedInputStream(con.getInputStream());
        }
            else if(background.type.equals("3")) {
            URL url = new URL("http://" + MainActivity.ip + "/Android/get_products.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                is = new BufferedInputStream(con.getInputStream());
        }
            else
            {
                System.out.println("kati xanni");
            }





        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //JSON
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            code = new String[ja.length()];
            productName = new String[ja.length()];
            productPrice = new String[ja.length()];
            productSeller = new String[ja.length()];
            productDescription = new String[ja.length()];
            image = new String[ja.length()];
            pquantity = new String[ja.length()];
            types = new String[ja.length()];



            for (int i = 0; i<ja.length(); i++)
            {
                jo = ja.getJSONObject(i);
                code[i] = jo.getString("eventID");
                productName[i] = jo.getString("title");
                productPrice[i] = jo.getString("productPrice");
                productSeller[i] = jo.getString("customerID");
                productDescription[i] = jo.getString("description");
                image[i] = jo.getString("productPicture");
                pquantity[i] = jo.getString("quantity");
                types[i] = jo.getString("type");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void takeToCart(View view)
    {

    }
}

