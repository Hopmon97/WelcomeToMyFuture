package com.example.welcometomyfuture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.BreakIterator;

public class OrderDetailsAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] perigrafi;
    private final String[] price;
    private final String[] posotita
            ;

    Bitmap bitmap;
    public OrderDetailsAdapter(Activity context,String[]perigrafi,String[]price, String[] posotita)
    {
        super(context, R.layout.order_detail_row);
        this.context=context;
        this.perigrafi = perigrafi;
        this.price = price;
        this.posotita=posotita;

    }
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.order_detail_row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.Product.setText(perigrafi[position]);
        viewHolder.tvPrice.setText(price[position]);
        viewHolder.tvposotita.setText(posotita[position]);





        /*Picasso
                .with(context)
                .load(imagepath[position])
                .into(viewHolder.ivw);
        */

        return r;
    }




    class  ViewHolder
    {
        public BreakIterator Price;
        TextView Product, tvPrice, tvposotita;


        // ImageView ivw;

        ViewHolder(View v)
        {
            Product= v.findViewById(R.id.Product);
            tvPrice= v.findViewById(R.id.tvPrice);
            tvposotita= v.findViewById(R.id.tvposotita);


            // ivw=(ImageView)v.findViewById(R.id.iv);
        }
    }


}

