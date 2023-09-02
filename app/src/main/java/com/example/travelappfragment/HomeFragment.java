package com.example.travelappfragment;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Places> placesArrayList;
    private String[] placeName;
    private int[] imgSourceID;
    private String[] placeDes;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        ImageView img_view = (ImageView) v.findViewById(R.id.noti_icon);
        ImageView img_avatar = (ImageView) v.findViewById(R.id.avatar);
        Button btn_place = (Button) v.findViewById(R.id.btn_place);
        Button btn_entertainment = (Button) v.findViewById(R.id.btn_entertainment);
        Button btn_restaurant = (Button) v.findViewById(R.id.btn_restaurant);
        Button btn_hotel = (Button) v.findViewById(R.id.btn_hotel);

        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);

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
                Intent intent = new Intent(getActivity(), HotelActitvity.class);
                startActivity(intent);
            }
        });

        return v;
    }
    //Day la phan code cho places
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.rcm_rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        HomePlaceAdapter homePlaceAdapter = new HomePlaceAdapter(getContext(), placesArrayList);
        recyclerView.setAdapter(homePlaceAdapter);
        homePlaceAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        placesArrayList = new ArrayList<>();

        placeName = new String[]{
                getString(R.string.PName1),
                getString(R.string.PName2),
                getString(R.string.PName3),
                getString(R.string.PName4),

        };

        placeDes = new String[]{
                getString(R.string.PDes1),
                getString(R.string.PDes2),
                getString(R.string.PDes3),
                getString(R.string.PDes4),
        };

        imgSourceID = new int[]{
                R.drawable.dalat,
                R.drawable.haiphong,
                R.drawable.hanoi,
                R.drawable.phuquoc
        };


        for (int i = 0; i < placeName.length; i++) {
            Places places = new Places(placeName[i], placeDes[i], imgSourceID[i]);
            placesArrayList.add(places);
        }
    }
}