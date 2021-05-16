package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.CustomCalendarView;
import com.example.myapplication.R;

public class Calendar extends Fragment {


    public Calendar() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //customCalendarView = (CustomCalendarView) getView().findViewById(R.id.custom_calendar_view);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}