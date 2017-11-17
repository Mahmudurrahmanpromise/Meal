package com.example.promise.meal.MealList;

/**
 * Created by promise on 11/7/17.
 */
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
import java.util.List;

public class MealListAdapter extends ArrayAdapter<MealUserInformation> {

    private static final String TAG = "AdapterAllUser";

    private Context mContext;
    int mResource;

    public MealListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MealUserInformation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String breakfast = getItem(position).getBreakfast();
        String lunch = getItem(position).getLunch();
        String dinner = getItem(position).getDinner();

        MealUserInformation user_information = new MealUserInformation(breakfast,lunch,dinner,date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.adapter_meallist, null);




        TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_date);
        TextView tvBreak = (TextView) convertView.findViewById(R.id.adapter_breakfast);
        TextView tvLunch = (TextView) convertView.findViewById(R.id.adapter_lunch);
        TextView tvDinner = (TextView) convertView.findViewById(R.id.adapter_dinner);

        tvDate.setText(date);
        tvBreak.setText(breakfast+"");
        tvLunch.setText(lunch+"");
        tvDinner.setText(dinner+"");

        return convertView;
    }
}
