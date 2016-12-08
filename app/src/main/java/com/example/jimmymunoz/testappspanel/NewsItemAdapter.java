package com.example.jimmymunoz.testappspanel;

/**
 * Created by jimmymunoz on 12/8/16.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsItemAdapter extends ArrayAdapter<NewsItem> {
    Context myContext;

    public NewsItemAdapter(Context context, ArrayList<NewsItem> arrayListData) {
        super(context, 0, arrayListData);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewsItem newitem = getItem(position);

        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_news_item, parent, false);
        }
        TextView textview_user = (TextView) convertView.findViewById(R.id.tv_title);
        textview_user.setText(newitem.getTitle());
        final ImageView impicture = (ImageView)convertView.findViewById(R.id.iv_picture);
        //ImageLoader.getInstance().displayImage(newitem.getPicture(), impicture, options, animateFirstListener);
        Picasso.with(getContext()).load(newitem.getPicture()).into(impicture);

        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        tv_type.setText(newitem.getType());

        TextView tv_place = (TextView) convertView.findViewById(R.id.tv_place);
        tv_place.setText(newitem.getData2());

        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        tv_date.setText(newitem.getData8());

        TextView tv_city = (TextView) convertView.findViewById(R.id.tv_city);
        tv_city.setText(newitem.getData7());

        TextView tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
        tv_hour.setText(newitem.getData10());

        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        tv_price.setText(newitem.getData12());

        TextView tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
        tv_distance.setText("0");
        final LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout);
        /*
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                impicture.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
            }
        });
        */

        convertView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                layout.setBackground(impicture.getDrawable());

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                //layout.setBackground(img2.getDrawable());
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}

