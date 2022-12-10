package com.example.welcometomyfuture;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class OrdersActivity extends AppCompatActivity {

    String urladdress="http://"+MainActivity.ip +"/Android/get_order_for_user.php";
    String[] orderID;
    String[] price;
    String[] date;

    ListView listView;
    BufferedInputStream is;
    String line= null;
    String result = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        listView= findViewById(R.id.recview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        GetOrder getOrder = new GetOrder(OrdersActivity.this);
        getOrder.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrdersActivity.this, OrderDetails.class);
                startActivity(intent);
            }
        });
    }

    public class GetOrder extends AsyncTask<String,Void,String> {

        Context context;

        public GetOrder(Context context) {
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
                orderID = new String[ja.length()];
                price = new String[ja.length()];
                date = new String[ja.length()];

                // imagepath = new String[ja.length()];

                for(int i=0;i<ja.length();i++)
                {
                    jo=ja.getJSONObject(i);
                    orderID[i]=jo.getString("orderID");
                    date[i]=jo.getString("dateOfOrder");
                    price[i]=jo.getString("totalPrice");

                    //imagepath[i]=jo.getString("photo");
                }

                OrdersAdabter customLiseView=new OrdersAdabter(OrdersActivity.this, orderID,price, date);
                listView.setAdapter(customLiseView);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                Toast.makeText(OrdersActivity.this, "No Orders Here", Toast.LENGTH_SHORT).show();
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



}