package com.example.promise.meal.BazarList;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.promise.meal.R;

import java.util.ArrayList;


public class BazarListAdapter extends ArrayAdapter<ObjectofBazarList> {

    private static final String TAG = "BazarListAdapter";

    private Context mContext;
    int mResource;


    public BazarListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ObjectofBazarList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String date = getItem(position).getDate();
        String name = getItem(position).getName();
        String cost = getItem(position).getCost();

        ObjectofBazarList objectofbazarlist = new ObjectofBazarList(date,name,cost);


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.date);
        TextView tvName = (TextView) convertView.findViewById(R.id.all_user_name);
        TextView tvCost = (TextView) convertView.findViewById(R.id.cost);

        tvDate.setText(date);
        tvName.setText(name);
        tvCost.setText(cost);

        return  convertView;

    }

}
