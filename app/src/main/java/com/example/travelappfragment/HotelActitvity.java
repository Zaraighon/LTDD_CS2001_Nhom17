package com.example.travelappfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.hotel_act, new ReviewFragment());
            fragmentTransaction.commit();
        } else if(menuItem.getItemId() == R.id.favorite){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.hotel_act, new FavoriteFragment());
            fragmentTransaction.commit();
            return true;
        }else if(menuItem.getItemId() == R.id.account){
            Intent intent = new Intent(HotelActitvity.this, EditProfile.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}