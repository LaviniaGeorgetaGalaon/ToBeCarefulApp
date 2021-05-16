package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.MenuActivity;
import com.example.myapplication.P1Activity;
import com.example.myapplication.P3Activity;
import com.example.myapplication.P4Activity;
import com.example.myapplication.P5Activity;
import com.example.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {
    private CircleImageView p1,p2,p3,p4,p5;
    Context context;
    ImageButton menuBtn;

    public Home() {
        // Required empty public constructor
    }

    public Home(int contentLayoutId, Context context) {
        super(contentLayoutId);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        androidx.appcompat.widget.Toolbar toolbar=view.findViewById(R.id.toolbar1);
        p1= view.findViewById(R.id.p1);
        p2= view.findViewById(R.id.p2);
        p3= view.findViewById(R.id.p3);
        p4= view.findViewById(R.id.p4);
        p5= view.findViewById(R.id.p5);
        menuBtn=view.findViewById(R.id.menuBtn);
        if(getActivity() != null)
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectFunction();
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.example.myapplication.p2.class);
                startActivity(intent);
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), P3Activity.class);
                startActivity(intent);
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), P4Activity.class);
                startActivity(intent);
            }
        });
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), P5Activity.class);
                startActivity(intent);
            }
        });
    }
    public void openSelectFunction() {
        Intent intent = new Intent(getContext(), P1Activity.class);
        startActivity(intent);
    }

}