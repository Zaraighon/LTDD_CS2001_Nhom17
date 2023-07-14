package com.example.travelappfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.travelappfragment.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding biding;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(biding.getRoot());
        replaceFragment(new HomeFragment());

        biding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int item_id = item.getItemId();

            if (item_id == R.id.home) {
                replaceFragment(new HomeFragment());
                return true;
            }
            else if (item_id == R.id.review ) {
                replaceFragment(new ReviewFragment());
                return true;
            }
            else if (item_id == R.id.favorite) {
                replaceFragment(new FavoriteFragment());
                return true;
            } else if (item_id == R.id.account) {
                replaceFragment(new AccountFragment());
                return true;
            }
            else
                return super.onContextItemSelected(item);
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime +2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
