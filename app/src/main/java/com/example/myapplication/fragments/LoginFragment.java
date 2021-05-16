package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private EditText  emailET, parolaET;
    private TextView signUpTV,forgotTV;
    private Button logInBtn,GoogleSignInBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX ="^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    private GoogleSignInClient mGoogleSignInClient;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener();
        
    }

    private void init(View view) {
        emailET=view.findViewById(R.id.emailET);
        parolaET=view.findViewById(R.id.parolaET);
        signUpTV=view.findViewById(R.id.signUpTV);
        forgotTV=view.findViewById(R.id.forgotTV);
        logInBtn=view.findViewById(R.id.logInBtn);
        GoogleSignInBtn=view.findViewById(R.id.GoogleSignInBtn);
        progressBar=view.findViewById(R.id.progressBar);
        auth= FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

    }

    private void clickListener()
    {

        forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new ForgotPassword());
            }
        });


        logInBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String email =emailET.getText().toString();
                String parola =parolaET.getText().toString();

                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailET.setError("Va rugam introduceți un email valid");
                    return;
                }
                if(parola.isEmpty() || parola.length()<6){
                    parolaET.setError("Va rugam introduceți o parolă validă de 6 caractere");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,parola)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user=auth.getCurrentUser();
                                    if(!user.isEmailVerified()){
                                        Toast.makeText(getContext(),"Va rugam verificati email-ul",Toast.LENGTH_SHORT).show();
                                    }

                                    sendUserToMainActivity();

                                }else{
                                    String exception="Error: "+task.getException().getMessage();
                                    Toast.makeText(getContext(),exception,Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                        });



            }
        });
        GoogleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    signIn();
            }
        });
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new CreateAccountFragment());

            }
        });

    }

    private void sendUserToMainActivity() {

        if(getActivity() == null)
            return;
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getActivity());


        Map<String, Object> map= new HashMap<>();
        map.put("name", account.getDisplayName());
        map.put("email", account.getEmail());
        map.put("profileImage", String.valueOf(account.getPhotoUrl()));
        map.put("uid", user.getUid());
        map.put("tip", "");
        map.put("pastile", "");
        map.put("doctor", "");

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            assert getActivity() !=null;
                            progressBar.setVisibility(View.GONE);
                            sendUserToMainActivity();

                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Error: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}