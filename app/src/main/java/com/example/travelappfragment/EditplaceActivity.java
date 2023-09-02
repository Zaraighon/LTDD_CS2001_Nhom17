package com.example.travelappfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelappfragment.databinding.ActivityAddplaceBinding;
import com.example.travelappfragment.databinding.ActivityEditplaceBinding;

public class EditplaceActivity extends AppCompatActivity {
    EditText edt_title, edt_address, edt_describe, edt_price, edt_time;

    ActivityEditplaceBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditplaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        edt_title = findViewById(R.id.title);
        edt_address = findViewById(R.id.address);
        edt_time = findViewById(R.id.time);
        edt_describe = findViewById(R.id.describe);
        edt_price = findViewById(R.id.price);




        databaseHelper = new DatabaseHelper(this);


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.title.getText().toString();
                int n = databaseHelper.getWritableDatabase().delete("place","title = ?",new String[]{title});
                if (n == 0){
                    Toast.makeText(EditplaceActivity.this, "no record delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditplaceActivity.this, "Xóa rồi load lại mà xem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.title.getText().toString();
                String address = binding.address.getText().toString();
                String time = binding.time.getText().toString();
                String price = binding.price.getText().toString();
                String describe = binding.describe.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("address",address);
                contentValues.put("time",time);
                contentValues.put("price",price);
                contentValues.put("describe",describe);
                int n = databaseHelper.getWritableDatabase().update("place",contentValues,"title = ?",new String[]{title});
                if (n == 0){
                    Toast.makeText(EditplaceActivity.this, "no record update", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditplaceActivity.this, "update successfully load lại mà xem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}