package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {
    ImageButton back;
    Button info, fb, insta,depre;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        fb = findViewById(R.id.fb);
        insta = findViewById(R.id.insta);
        depre = findViewById(R.id.depre);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(MenuActivity.this).inflate(R.layout.despre_noi,null);
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.lhl.no/globalassets/lhl-internasjonal/dokumenter/tuberculosis-brochure-tub-rumensk-_-print2017.pdf"));
                startActivity(viewIntent);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0800080020";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });
        depre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://mentalhealthforromania.org/ro/consiliere-gratuita"));
                startActivity(viewIntent);
            }
        });
    }
}
