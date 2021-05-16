package com.example.myapplication.fragments;
//package com.vogella.android.listview.withanimation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.FragmentReplacerActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.PostTextModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends Fragment {

    private TextView TipET,DoctorET;
    private TextView usernameET;
    private CircleImageView profileImage;
    private Button deconectBtn,save;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final  String TAG = "Profil";

    private FirebaseUser user;
    DocumentReference userRef;

    public Profil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        init(view);
        loadBasicData();

    }
    private void init(View view) {
        Toolbar toolbar=view.findViewById(R.id.toolbar1);
        assert getActivity() !=null;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        TipET=view.findViewById(R.id.tipET);
        DoctorET=view.findViewById(R.id.DoctorET);
        usernameET=view.findViewById(R.id.usernameET);
        profileImage=view.findViewById(R.id.profileImage);
        deconectBtn=(Button) view.findViewById(R.id.deconectBtn);
        save = (Button) view.findViewById(R.id.save);

        FirebaseAuth auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        userRef= FirebaseFirestore.getInstance().collection("Users")
                .document(user.getUid());
        setupFirebaseListener();

        deconectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sing out the user");
                FirebaseAuth.getInstance().signOut();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip = TipET.getText().toString();
                userRef.update("tip",tip);
                String infoD = DoctorET.getText().toString();
                userRef.update("infoDoctor",infoD);
                loadBasicData();
            }
        });


    }

    private void setupFirebaseListener(){
        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in"+ user.getUid());
                }else{
                    Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),FragmentReplacerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    private void loadBasicData() {


        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    return;
                if (value.exists()){
                    String name = value.getString("name");
                    String tip = value.getString("tip");
                    String infoDoctor = value.getString("infoDoctor");
                    String profileURL = value.getString("profileImage");

                    usernameET.setText(name);
                    TipET.setText(tip);
                    DoctorET.setText(infoDoctor);

                    Glide.with(getContext().getApplicationContext())
                            .load(profileURL)
                            .placeholder(R.drawable.profile__fill)
                            .timeout(6500)
                            .into(profileImage);

                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}