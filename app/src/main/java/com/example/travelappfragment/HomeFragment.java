package com.example.travelappfragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        ImageView img_view = (ImageView) v.findViewById(R.id.noti_icon);
        Button btn_place = (Button) v.findViewById(R.id.btn_place);
        Button btn_entertainment = (Button) v.findViewById(R.id.btn_entertainment);
        Button btn_restaurant = (Button) v.findViewById(R.id.btn_restaurant);
        Button btn_hotel = (Button) v.findViewById(R.id.btn_hotel);
        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity() ,"Notification clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity() ,"Place clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btn_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity() ,"Entertainment clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity() ,"Restaurant clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btn_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity() ,"Hotel clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}