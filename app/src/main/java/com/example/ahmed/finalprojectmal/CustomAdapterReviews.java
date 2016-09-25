package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahmed on 23/09/2016.
 */
public class CustomAdapterReviews extends BaseAdapter {
    ArrayList<reviews> reviewsArrayList;
    Context context;

    public CustomAdapterReviews(ArrayList<reviews> reviewsArrayList, Context context) {
        this.reviewsArrayList = reviewsArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviewsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater.inflate(R.layout.layout_reviews_list,null);

        TextView auther=(TextView)view.findViewById(R.id.TextViewAuthe);
        TextView content=(TextView)view.findViewById(R.id.TextViewContent);
        auther.setText(reviewsArrayList.get(position).getAuther());
      content.setText(reviewsArrayList.get(position).getContent());

        return view;
    }
}
