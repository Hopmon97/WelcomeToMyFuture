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

public class OrdersAdabter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] orderID;
    private final String[] price;
    private final String[] dateOfOrder;


    Bitmap bitmap;
    public OrdersAdabter(Activity context, String[] orderID,String[]price, String[] dateOfOrder)
    {
        super(context, R.layout.orders_row,orderID);
        this.context=context;
        this.orderID=orderID;
        this.price = price;
        this.dateOfOrder=dateOfOrder;

    }
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.orders_row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.tvid.setText(orderID[position]);
        viewHolder.tvprice.setText(price[position]);
        viewHolder.tvDate.setText(dateOfOrder[position]);






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
        TextView tvid, tvDate, tvprice;


        // ImageView ivw;

        ViewHolder(View v)
        {
            tvid= v.findViewById(R.id.tvOrderID);
            tvprice= v.findViewById(R.id.tvTotalPrice);
            tvDate= v.findViewById(R.id.tvDateOfOrder);


            // ivw=(ImageView)v.findViewById(R.id.iv);
        }
    }


}

