package com.example.myapplication.fragments;

import android.content.Intent;
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
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountFragment extends Fragment {

    private EditText nameET, emailET, parolaET, confirmaParolaET;
    private ProgressBar progressBar;
    private TextView loginTV;
    private Button signUpBtn;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX ="^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener();

        
    }

    private void clickListener() {

        loginTV.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name =nameET.getText().toString();
                String email =emailET.getText().toString();
                String parola =parolaET.getText().toString();
                String confirmaParola =confirmaParolaET.getText().toString();

                if(name.isEmpty() || name.equals(" ")){
                    nameET.setError("Va rugam introduce??i un nume valid");
                    return;
                }
                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailET.setError("Va rugam introduce??i un email valid");
                    return;
                }
                if(parola.isEmpty() || parola.length()<6){
                    parolaET.setError("Va rugam introduce??i o parol?? valid??");
                    return;
                }
                if(!parola.equals(confirmaParola)){
                    confirmaParolaET.setError("Parola introdus?? nu este aceeasi!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                createAccount(name,email,parola);
            }
        });
    }

    private void createAccount(String name,String email,String parola){

        auth.createUserWithEmailAndPassword(email,parola)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user=auth.getCurrentUser();
                            user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(),"Email de verificare trimis!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                            uploadUser(user,name,email);


                        }else {
                            progressBar.setVisibility(View.GONE);
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(),"Error: "+exception,Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void uploadUser(FirebaseUser user, String name,String email){
        Map<String, Object> map= new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("profileImage", " ");
        map.put("tip", "");
        map.put("pastile", "");
        map.put("doctor", "");
        map.put("uid", user.getUid());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
            .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            assert getActivity() !=null;
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity().getApplicationContext(),
                                    MainActivity.class));
                            getActivity().finish();

                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Error: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }


    private void init(View view) {
        nameET=view.findViewById(R.id.nameET);
        emailET=view.findViewById(R.id.emailET);
        parolaET=view.findViewById(R.id.parolaET);
        confirmaParolaET=view.findViewById(R.id.cornfirmaParolaET);
        loginTV=view.findViewById(R.id.loginTV);
        signUpBtn=view.findViewById(R.id.signUpBtn);
        progressBar=view.findViewById(R.id.progressBar);

        auth= FirebaseAuth.getInstance();

    }

}