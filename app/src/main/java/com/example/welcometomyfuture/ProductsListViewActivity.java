package com.example.welcometomyfuture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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


public class ProductsListViewActivity extends ArrayAdapter<String> {

    private final Activity context;


    private final String[] pID;
    private final String[] pName;
    private final String[] pPrice;
    private final String[] pSeller;
    private final String[] pDescription;
    private final String[] image;
    private final String[] pquantity;
    private final String[] typelist;

    Button btnDeleteProducts;



    Bitmap bitmap;
    ViewHolder viewHolder;
    public ProductsListViewActivity(Activity context, String[] pID, String[] pName, String[] pPrice, String[] pSeller, String[] pDescription, String[] image,String[]pquantity,String[]typelist) {
        super(context, R.layout.activity_products, pID);
        this.context = context;
        this.pID = pID;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pSeller = pSeller;
        this.pDescription = pDescription;
        this.image = image;
        this.pquantity = pquantity;
        this.typelist=typelist;

    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder= null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.activity_products, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tvIDD.setText(pID[position]);
        viewHolder.tvName.setText(pName[position]);
        viewHolder.tvPrice.setText(pPrice[position]);
        viewHolder.tvSeller.setText(pSeller[position]);
        viewHolder.tvpDescription.setText(pDescription[position]);
        System.out.println(image[position]);
        viewHolder.tvQuantity.setText(pquantity[position]);

        Picasso
                .with(context)
                .load(image[position])
                .into(viewHolder.ivw);

        if(typelist[position].equals("1"))
        {
            viewHolder.ptype.setText("Farmer");
        }
        else if(typelist[position].equals("2"))
        {
            viewHolder.ptype.setText("Agronomist");
        }
        else if(typelist[position].equals("3"))
        {
            viewHolder.ptype.setText("Farmer and Agronomist");
        }



        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProducts delete = new deleteProducts(context);
                delete.execute(pID[position]);
            }
        });


        return r;
    }


    class ViewHolder {
        TextView tvIDD, tvName, tvPrice, tvSeller, tvpDescription,tvQuantity,ptype;
         Button delete;
         ImageView ivw;

        ViewHolder(View v) {
            tvIDD = v.findViewById(R.id.tvPID);
            tvName = v.findViewById(R.id.tvPName);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvSeller = v.findViewById(R.id.tvSeller);
            tvpDescription = v.findViewById(R.id.tvDescription);
            ivw=(ImageView)v.findViewById(R.id.ivw);
            tvQuantity = v.findViewById(R.id.tvQuantity);
            ptype = v.findViewById(R.id.ptype);
            delete = v.findViewById(R.id.btnDeleteProducts);
            if (background.type.equals("0"))
                delete.setVisibility(View.VISIBLE);
        }
    }


    public class deleteProducts extends AsyncTask<String,Void,String> {

        Context context;

        public deleteProducts(Context context) {
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
                    Intent intent = new Intent(context, ProductsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... voids) {

            String eventID = voids[0];
            String result = "";

            String connstr = "http://" + MainActivity.ip + "/Android/delete_button_products.php";

            //UPDATE customer set customerName = '$customerName', customerSurname= '$customerSurname'.... WHERE customerID = '$customerID'
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("eventID", "UTF-8") + "=" + URLEncoder.encode(eventID, "UTF-8");

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




