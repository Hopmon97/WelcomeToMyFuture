package com.example.welcometomyfuture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONObject;

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

public class background extends AsyncTask<String,Void,String> {

    public static String customerID;
    public static String type;
    public static String customerName;
    public static String customerSurname;
    public static String password;
    public static String email;
    public static String address;
    public static String city;
    public static String country;
    public static String postalcode;
    public static String phone;

    Bundle data = new Bundle();

    AlertDialog dialog;
    Context context;
    public background(Context context)
    {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("loginfailed"))
        {
            dialog.setMessage(result);
            dialog.show();
        }
        else
        {
            try{
                JSONObject jo = new JSONObject(result);
                type=jo.getString("type");

                customerID = jo.getString("customerID");
                customerName= jo.getString("customerName");
                customerSurname= jo.getString("customerSurname");
                password= jo.getString("password");
                email= jo.getString("email");
                address= jo.getString("address");
                city= jo.getString("city");
                country= jo.getString("country");
                postalcode= jo.getString("postalcode");
                phone= jo.getString("phone");

                System.out.println("id " + customerID + "name " + customerName + "surname " + customerSurname );


               /* data.putString("customerID", customerID);
                data.putString("customerName", customerName);
                data.putString("customerSurname", customerSurname);
                data.putString("password", password);
                data.putString("email", email);
                data.putString("address", address);
                data.putString("city", city);
                data.putString("postalcode", postalcode);
                data.putString("phone", phone);
                */

                if (type.equals("0"))
                {

                    Intent intent = new Intent(context, admin.class);
                    context.startActivity(intent);

                }
                else if (type.equals("1"))
                {

                    Intent intent = new Intent(context, Geoponos.class);
                    context.startActivity(intent);

                }
                else if (type.equals("2"))
                {

                    Intent intent = new Intent(context, Georgosss.class);
                    context.startActivity(intent);

                }
                else if (type.equals("3"))
                {

                    Intent intent = new Intent(context,SellerActivity.class);
                    context.startActivity(intent);

                }


            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... voids){
        String result = "";
        String user = voids[0];
        String pass = voids[1];


        String connstr ="http://"+MainActivity.ip+"/Android/login.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            String data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&&" +
                    URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(ips, StandardCharsets.ISO_8859_1));
            String line= "";
            while((line=reader.readLine())!=null)
            {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;


        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e){
            result= e.getMessage();
        }


        return result;
    }
}


