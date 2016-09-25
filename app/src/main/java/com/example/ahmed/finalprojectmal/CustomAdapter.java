package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed on 21/09/2016.
 */
public class CustomAdapter  extends BaseAdapter {
    Context context;
    ArrayList<MovieDB> ListData;
    CustomAdapter(Context context,ArrayList<MovieDB> myList)
    {
        this.context=context;
        this.ListData=myList;
    }

    @Override
    public int getCount() {
        return ListData.size();
    }

    @Override
    public Object getItem(int position) {

        return ListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
         View view=inflater.inflate(R.layout.grid_layout,null);
         ImageView img= (ImageView) view.findViewById(R.id.posterImg);


        Picasso.with(context).load(ListData.get(position).getPoster()).into(img);



        return view;

    }
}


