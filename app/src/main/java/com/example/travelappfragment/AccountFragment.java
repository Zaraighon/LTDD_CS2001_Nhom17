package com.example.travelappfragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class AccountFragment extends Fragment {

    Button cancel_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        Button btn_logout = (Button) v.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
        return v;
    }

    private void showLogoutDialog(){
        View alertCustomDialog = LayoutInflater.from(requireContext()).inflate(R.layout.logout_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());

        alertDialog.setView(alertCustomDialog);
        cancel_btn = (Button) alertCustomDialog.findViewById(R.id.logout_cancel);

        final AlertDialog dialog = alertDialog.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
}