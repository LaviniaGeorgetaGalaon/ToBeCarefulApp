package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FragmentReplacerActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.myapplication.fragments.CreateAccountFragment.EMAIL_REGEX;


public class ForgotPassword extends Fragment {

    private TextView loginTV;
    private Button recoverBtn;
    private EditText emailET;

    private FirebaseAuth auth;

    private ProgressBar progressBar;

    public ForgotPassword() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener();
    }

    private void clickListener() {
        loginTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });
        recoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=emailET.getText().toString();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailET.setError("Va rugam introduceți un email valid");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getContext(),"S-a trimis un email pentru resetarea parolei!", Toast.LENGTH_SHORT).show();
                            emailET.setText("");
                        }else {
                            String errMsg = task.getException().getMessage();
                            Toast.makeText(getContext(),"Eroare: "+errMsg,Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void init(View view) {
        loginTV=view.findViewById(R.id.loginTV);
        recoverBtn=view.findViewById(R.id.recoverBtn);
        emailET=view.findViewById(R.id.emailET);
        progressBar=view.findViewById(R.id.progressBar);


        auth=FirebaseAuth.getInstance();

    }
}