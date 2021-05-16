package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.fragments.Profil;
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
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PastileView extends LinearLayout {
    EditText et,editTextRemove;
    Button add, button_remove;
    Context context;
    ListView lv;
    String pastile;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    private FirebaseUser user;
    DocumentReference userRef;

    public PastileView(Context context) {
        super(context);
    }

    public PastileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Init();
        //loadArray();
        onCreate();
    }
    private void Init() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_pastile_list, this);
        et = (EditText) findViewById(R.id.pastileName);
        add = (Button)findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.listaP);
        button_remove =(Button)findViewById(R.id.button_remove);
        editTextRemove = findViewById(R.id.edittext_remove);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        userRef= FirebaseFirestore.getInstance().collection("Pastile")
                .document("zbEveRYQLlMa7JsObBfv");
        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });

    }
    public void removeItem(int position) {
        String result2 = "";
        //arrayList.remove(position);
        for(int i=0;i<arrayList.size();i++){
            if(i!=position)
                result2=result2+" "+arrayList.get(i);
        }
        arrayList.clear();
        userRef.update("pastila", result2);
        //adapter.notifyDataSetChanged();
    }
    public PastileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void onCreate() {
        arrayList = new ArrayList<String>();
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    return;
                if (value.exists()){
                    pastile = value.getString("pastila");
                    List<String> list = new ArrayList<String>(Arrays.asList(pastile.split(" ")));
                    for(int i=0;i<list.size();i++){
                        arrayList.add(list.get(i));
                    }
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,arrayList);
                    //adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);

                }
            }
        });
        onBtnClick();
    }

    public void onBtnClick(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadArray();
                String result = et.getText().toString();
                pastile = pastile+" "+result;
                userRef.update("pastila", pastile);
                arrayList.clear();
                //arrayList.add(result);
                //adapter.notifyDataSetChanged();
            }
        });
    }


}