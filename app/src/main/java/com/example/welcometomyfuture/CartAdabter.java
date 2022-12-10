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

public class CartAdabter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] pname;
    private final String[] price;
    private final String[] quantity;


    Bitmap bitmap;
    public CartAdabter(Activity context, String[] pname,String[]price, String[]quantity)
    {
        super(context, R.layout.cart_row,pname);
        this.context=context;
        this.pname=pname;
        this.price = price;
        this.quantity=quantity;

    }
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.cart_row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
        viewHolder.tvpname.setText(pname[position]);
        viewHolder.tvprice.setText(price[position]);
        viewHolder.tvQuan.setText(quantity[position]);






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
        TextView tvid, tvpname, tvprice, tvQuan;


        // ImageView ivw;

        ViewHolder(View v)
        {
            tvid= v.findViewById(R.id.orderID);
            tvpname= v.findViewById(R.id.tvpname);
            tvprice= v.findViewById(R.id.tvprice);
            tvQuan= v.findViewById(R.id.tvQuan);


            // ivw=(ImageView)v.findViewById(R.id.iv);
        }
    }


}

