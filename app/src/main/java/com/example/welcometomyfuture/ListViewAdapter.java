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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] customer;
    private final String[] user;
    private final String[] name;
    private final String[] surname;
    private final String[] email;
    private final String[] address;
    private final String[] city;
    private final String[] country;
    private final String[] postalcode;
    private final String[] phone;
    private final String[] typelist;

    Bitmap bitmap;
    public ListViewAdapter(Activity context, String[] customer,String[] user, String[] name,String[]surname, String[]email, String[]address, String[]city, String[]country, String[]postalcode, String[]phone, String[]typelist)
    {
        super(context, R.layout.layout,name);
        this.context=context;
        this.customer=customer;
        this.user=user;
        this.name=name;
        this.surname = surname;
        this.email=email;
        this.address=address;
        this.city=city;
        this.country=country;
        this.postalcode=postalcode;
        this.phone=phone;
        this.typelist=typelist;
    }
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.tvIDD.setText(customer[position]);
        viewHolder.tvUser.setText(user[position]);
        viewHolder.tvName.setText(name[position]);
        viewHolder.tvSurname.setText(surname[position]);
        viewHolder.tvEmail.setText(email[position]);
        viewHolder.tvAddress.setText(address[position]);
        viewHolder.tvCity.setText(city[position]);
        viewHolder.tvCountry.setText(country[position]);
        viewHolder.tvPostalcode.setText(postalcode[position]);
        viewHolder.tvPhone.setText(phone[position]);




        if(typelist[position].equals("0"))
        {
            viewHolder.tvType.setText("Admin");
        }
        else if(typelist[position].equals("1"))
        {
            viewHolder.tvType.setText("Gewrgos");
        }
        else if(typelist[position].equals("2"))
        {
            viewHolder.tvType.setText("Geoponos");
        }else if(typelist[position].equals("3"))
        {
            viewHolder.tvType.setText("Promitheftis");
        }

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer delete = new deleteCustomer(context);
                delete.execute(customer[position]);
            }
        });




        /*Picasso
                .with(context)
                .load(imagepath[position])
                .into(viewHolder.ivw);
        */

        return r;
    }




    class  ViewHolder
    {
        public BreakIterator tvPrice;
        TextView tvIDD,tvUser, tvName, tvSurname, tvEmail, tvAddress, tvCity, tvCountry, tvPostalcode, tvPhone, tvType;
        Button delete;

       // ImageView ivw;

        ViewHolder(View v)
        {
            tvIDD= v.findViewById(R.id.tvPID);
            tvUser= v.findViewById(R.id.tvUser);
            tvName= v.findViewById(R.id.tvPName);
            tvSurname= v.findViewById(R.id.tvPrice);
            tvEmail= v.findViewById(R.id.tvSeller);
            tvAddress= v.findViewById(R.id.tvDescription);
            tvCity= v.findViewById(R.id.tvCity);
            tvCountry= v.findViewById(R.id.tvCountry);
            tvPostalcode= v.findViewById(R.id.tvPostalcode);
            tvPhone= v.findViewById(R.id.tvPhone);
            tvType= v.findViewById(R.id.tvType);
            delete = v.findViewById(R.id.btnDelete);

            // ivw=(ImageView)v.findViewById(R.id.iv);
        }
    }

    public class deleteCustomer extends AsyncTask<String,Void,String> {

        Context context;

        public deleteCustomer(Context context) {
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
                    Intent intent = new Intent(context, Geoponos.class);
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

            String customerID = voids[0];
            String result = "";

            String connstr = "http://" + MainActivity.ip + "/Android/delete_button.php";

            //UPDATE customer set customerName = '$customerName', customerSurname= '$customerSurname'.... WHERE customerID = '$customerID'
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("customer", "UTF-8") + "=" + URLEncoder.encode(customerID, "UTF-8");

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

