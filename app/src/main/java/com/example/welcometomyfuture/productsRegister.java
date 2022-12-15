package com.example.welcometomyfuture;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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

public class productsRegister extends AppCompatActivity {
    private EditText etProductName, etPrice, etDesc, Quantity;
    private String title, description, price, quantity, sType;

    BottomNavigationView bottom_navigation;
    BottomNavigationView bottom_navigation2;
    BottomNavigationView bottom_navigation3;
    BottomNavigationView bottom_navigation4;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_register);
        etProductName = findViewById(R.id.etProductsName);
        etPrice = findViewById(R.id.etPrice);
        etDesc = findViewById(R.id.etDesc);
        Quantity = findViewById(R.id.Quantity);
        iv=findViewById(R.id.ivUserImage);


        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation2 = findViewById(R.id.bottom_navigation2);
        bottom_navigation3 = findViewById(R.id.bottom_navigation3);
        bottom_navigation4 = findViewById(R.id.bottom_navigation4);

        /*if (background.type.equals("0")) {

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
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            return true;
                    }
                    return false;
                }
            });
        } else if (background.type.equals("1")) {
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


        } else if (background.type.equals("2")) {
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


        } else if (background.type.equals("3")) {
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


        }*/


    }

    public void saveProduct(View view) {
        title = etProductName.getText().toString().trim();
        description = etDesc.getText().toString().trim();
        price = etPrice.getText().toString().trim();
        quantity = Quantity.getText().toString().trim();


        System.out.println(" " + title + " " + description + " " + price + " " + quantity + " " + sType);

        if (title.equals("") || description.equals("") || price.equals("") || quantity.equals("")) {
            Toast.makeText(this, "Please fill all spaces", Toast.LENGTH_SHORT).show();

        } else {
            RegisterProduct registerUser = new RegisterProduct(this);
            registerUser.execute(title, description, price, quantity, sType, encodedImage);
        }
    }
    public class RegisterProduct extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog alertDialog;

        RegisterProduct(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String title = params[0];
                String description = params[1];
                String price = params[2];
                String quantity = params[3];
                String sType = params[4];
                String image = params[5];

                /*???? */
                URL url = new URL("http://" + MainActivity.ip + "/Android/ProductRegister.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&"


                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"

                        + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") + "&"


                        + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"

                        + URLEncoder.encode("img", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8") + "&"

                        + URLEncoder.encode("customerID", "UTF-8") + "=" + URLEncoder.encode(background.customerID, "UTF-8") + "&"


                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(sType, "UTF-8");


                //URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"....


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        //on post execution of the worker, we print the message that we got from the php script
        @Override
        protected void onPostExecute(String result) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Register Status:");
            alertDialog.setMessage(result);
            alertDialog.show();

            Intent intent = new Intent(productsRegister.this,SellerActivity.class);
            startActivity(intent);
            finish();
        }

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbFarmer:
                if (checked) {
                    sType = "1";
                    break;
                }

            case R.id.rbArgo:
                if (checked) {
                    sType = "2";
                    break;
                }

            case R.id.rbBoth:
                if (checked) {
                    sType = "3";
                    break;
                }

        }
    }

    public void addImage(View view)
    {
        System.out.println("plz add img");

        if(checkPermissions())
        {

            takePictureFromCamera();

        }
        else
        {
            takePictureFromCamera();

        }
    }

    Bitmap bitmap;
    String encodedImage="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImageUri = data.getData();

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        iv.setImageURI(selectedImageUri);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                        byte[] imageBytes = stream.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                break;

            case 2:
                if(resultCode == RESULT_OK)
                {
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap)bundle.get("data");
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageBitmap(bitmap);

                    try {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                        byte[] imageBytes = stream.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }


                }
        }
    }


    public void takePictureFromCamera()
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 2);
    }

    public boolean checkPermissions()
    {
        if(Build.VERSION.SDK_INT>=23)
        {
            int cameraPermission = ActivityCompat.checkSelfPermission(productsRegister.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(productsRegister.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

}