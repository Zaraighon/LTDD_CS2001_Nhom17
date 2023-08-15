package com.example.travelappfragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ReviewFragment extends Fragment {

    ListView recylerview;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseHelper databaseHelper;
    FloatingActionButton btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_review, container, false);
        btnAdd =  v.findViewById(R.id.add_travel);
         recylerview = v.findViewById(R.id.recyler_view);
         databaseHelper = new DatabaseHelper(requireContext());
         list = new ArrayList<>();
         adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,list);
         recylerview.setAdapter(adapter);


        list.clear();
        Cursor c = databaseHelper.getReadableDatabase().query("place",null,null,null,null,null,null);
        c.moveToNext();
        String data = "";
        while (c.isAfterLast() == false)
        {
            data = c.getString(0)+"-"+c.getString(1);
            c.moveToNext();
            list.add(data);
        }
        c.close();
        adapter.notifyDataSetChanged();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddplaceActivity.class);
                startActivity(intent);
            }
        });

        recylerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),EditplaceActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
