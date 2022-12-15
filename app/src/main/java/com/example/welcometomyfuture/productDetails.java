package com.example.welcometomyfuture;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

public class productDetails extends AppCompatActivity {

    String productID, name, description, price, seller, image, quantity, new_price,totalq;

    TextView tvID, tvName, tvDescription, tvPrice, tvSeller;
    ImageView iv;
    EditText etQuantity;
    Button btnCart;

    Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvID=findViewById(R.id.tvID);
        tvName=findViewById(R.id.tvName);
        tvDescription=findViewById(R.id.tvDescription);
        tvPrice=findViewById(R.id.tvPrice);
        tvSeller=findViewById(R.id.tvSeller);
        etQuantity=findViewById(R.id.etQuantity);
        iv=findViewById(R.id.iv);
        btnCart=findViewById(R.id.btnCart);

        Bundle data = getIntent().getExtras();

        productID=data.getString("ID");
        name=data.getString("name");
        description=data.getString("description");
        price=data.getString("price");
        seller=data.getString("seller");
        image=data.getString("image");
        totalq=data.getString("totalq");

        tvID.setText(productID);
        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText(price);
        tvSeller.setText(seller);

        Picasso
                .with(this)
                .load(image)
                .into(iv);


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etQuantity.getText().toString().equals(""))
                {
                   quantity = "1";
                }
                else
                {
                    quantity = etQuantity.getText().toString();
                }



                System.out.println("price "+price);
                System.out.println("quantity " +quantity);

                if (Integer.parseInt(quantity) > Integer.parseInt(totalq))
                {
                    Toast.makeText(productDetails.this, "Your Quantity is greater that the available  Quantity. The available quantity is: "+totalq, Toast.LENGTH_LONG).show();
                }
                else {


                    new_price = Double.toString(Double.valueOf(price) * Double.valueOf(quantity));

                    System.out.println("total_price  " + new_price);


                    AddCart addCart = new AddCart(productDetails.this);
                    addCart.execute();
                }
            }
        });

    }



    public class AddCart extends AsyncTask<String,Void,String>
    {
        Context context;
        String result = "";

        public AddCart(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(String result) {

            System.out.println(result);

            try {
                if (result.equals("success")) {
                    Intent intent = new Intent(productDetails.this, Cart.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(context, "Product has been added to the cart sucesfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "Error adding product to cart", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... voids) {
            String connstr = "http://" + MainActivity.ip + "/Android/add_to_cart.php";

            //UPDATE customer set customerName = '$customerName', customerSurname= '$customerSurname'.... WHERE customerID = '$customerID'
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("customerID", "UTF-8") + "=" + URLEncoder.encode(background.customerID, "UTF-8")+ "&&" +
                        URLEncoder.encode("productID", "UTF-8") + "=" + URLEncoder.encode(productID, "UTF-8") + "&&" +
                        URLEncoder.encode("productName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&&" +
                        URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(new_price, "UTF-8") + "&&" +
                        URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8");



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