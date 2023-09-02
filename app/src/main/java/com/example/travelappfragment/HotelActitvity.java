package com.example.travelappfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.travelappfragment.databinding.ActivityEditplaceBinding;

import java.util.zip.Inflater;

public class HotelActitvity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ImageButton btn_back, btn_menu;
    RecyclerView rv_hotel;
    ActivityEditplaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_actitvity);
        btn_back = findViewById(R.id.back_btn1);
        btn_menu = findViewById(R.id.menu_btn1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }
        });


    }
    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.botton_nav_menu);
        popup.show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.home){
            finish();
            return true;
        } else if(menuItem.getItemId() == R.id.review){
            return true;
        } else if(menuItem.getItemId() == R.id.favorite){
            return true;
        }else if(menuItem.getItemId() == R.id.account){
            return true;
        }
        return true;
    }
}