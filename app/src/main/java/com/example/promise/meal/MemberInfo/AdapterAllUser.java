package com.example.promise.meal.MemberInfo;


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

public class AdapterAllUser extends ArrayAdapter<AllUserInformation> {

    private static final String TAG = "AdapterAllUser";

    private Context mContext;
    int mResource;

    public AdapterAllUser(@NonNull Context context, @LayoutRes int resource, @NonNull List<AllUserInformation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String email = getItem(position).getEmail();
        String name = getItem(position).getName();
        String phone = getItem(position).getPhone_num();

        AllUserInformation user_information = new AllUserInformation(email,name,phone);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.adapter_alluser, null);




        TextView tvEmail = (TextView) convertView.findViewById(R.id.all_user_email);
        TextView tvName = (TextView) convertView.findViewById(R.id.all_user_name);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.all_user_phone);

        tvEmail.setText(email.toString());
        tvName.setText(name.toString());
        tvPhone.setText(phone.toString());

        return convertView;
    }
}
