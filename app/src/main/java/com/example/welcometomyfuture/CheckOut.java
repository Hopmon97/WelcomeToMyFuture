package com.example.welcometomyfuture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CheckOut extends AppCompatActivity {
    String urladdress = "http://" + MainActivity.ip + "/Android/customer.php";//rwta stelioooo
    String name;
    String phone;
    String Date;
    String email;
    String address;
    String postal;

    String total;
    EditText etUsername, etPhone, etEmail,etAddress,postalcode;
    TextView priceTotal, date;

    //paypal initializers
    PayPalConfiguration m_configuration;
    String m_paypalCliendId = "Ac8UiKFxgFJc0WF2q6Pp-N6tS5MJaaWMNVlTRDr4YgD0bSjhPCF8KVvLGkdymHP8BB68PiJJSAJQa9PJ";
    Intent m_service;
    int m_paypalRequestCode = 999;

    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //paypal configuration, intent to paypal class
        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // SANDBOX FOR TEST,  PRODUCTION FOR REAL
                .clientId(m_paypalCliendId);
        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        startService(m_service); //paypal service, listening to calls to paypal app


        Bundle data = getIntent().getExtras();

        priceTotal=findViewById(R.id.price);


        priceTotal.setText(data.getString("total"));

        etUsername = findViewById(R.id.etUser);
        etPhone= findViewById(R.id.phone);
        date = findViewById(R.id.date);
        etEmail = findViewById(R.id.email);
        etAddress = findViewById(R.id.Address);
        postalcode = findViewById(R.id.postalcode);

        System.out.println("this " + background.customerID);

        etUsername.setText(background.customerName);
        etPhone.setText(background.phone);
        etEmail.setText(background.email);
        etAddress.setText(background.address);
        postalcode.setText(background.postalcode);

        java.util.Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDate = df.format(c);

        date.setText(currentDate);

    }

    public void Payment (View view)
    {
        name = etUsername.getText().toString();
        background.customerName = name;
        phone = etPhone.getText().toString();
        background.phone = phone;
        email = etEmail.getText().toString();
        background.email = email;
        address = etAddress.getText().toString();
        background.address = address;
        postal = postalcode.getText().toString();
        background.postalcode = postal;
        total = priceTotal.getText().toString();
        Date = date.getText().toString();

        //AddOrder orderWorker = new AddOrder(this);
        //orderWorker.execute();
        pay();

    }



    //paypal payment class
    public void pay()
    {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(Double.valueOf(total)), "EUR", "Test Payment Paypal", PayPalPayment.PAYMENT_INTENT_SALE );
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent,m_paypalRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        AlertDialog alertDialog;

        if(requestCode == m_paypalRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                //confirmation that payment worked
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved")) //if payment worked and state is approved
                    {
                        //if payment is approved, call orderWorker that inserts the order to the database
                        //insert new order
                        AddOrder orderWorker = new AddOrder(this);
                        orderWorker.execute();

                    }
                    else
                    {
                        alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("Payment Status");
                        alertDialog.setMessage("Payment Failed");
                        alertDialog.show();
                    }
                }
                else
                {
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Payment Failed");
                    alertDialog.setMessage("Payment Failed");
                    alertDialog.show();
                }
            }
        }
    }

    //inserts the order to the database and goes back to the home screen after insertion

    public class AddOrder extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        AddOrder(Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String order_url ="http://"+MainActivity.ip+"/Android/add_order.php";

            try {
                URL url = new URL(order_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data = URLEncoder.encode("customerID","UTF-8")+"="+URLEncoder.encode(background.customerID,"UTF-8")+"&"
                        +URLEncoder.encode("totalPrice","UTF-8")+"="+URLEncoder.encode(total,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(currentDate,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
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
        protected void onPreExecute()
        {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Order Status:");
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println(result);
            if(result.equals("success"))
            {
                Toast.makeText(CheckOut.this, "Your order have been submitted successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckOut.this,OrdersActivity.class);
                startActivity(intent);
                finish();


            }
            else
            {
                Toast.makeText(CheckOut.this, "Error submitting your order", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
