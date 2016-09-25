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
public class CustomAdapterTrailer extends BaseAdapter {
CustomAdapterTrailer(Context context,ArrayList<Trailer> arrayList){
    this.context=context;
    this.arrayList=arrayList;
}
    ArrayList<Trailer>arrayList;
    Context context;
    @Override
    public int getCount() {
        return arrayList.size();
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
        View view=inflater.inflate(R.layout.layout_trailer_list,null);

        TextView textView=(TextView)view.findViewById(R.id.text_view_trailer);
        textView.setText(arrayList.get(position).getName());
        return view;
    }
}
