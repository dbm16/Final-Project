package com.dbm.finalproject.fragments.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dbm.finalproject.Activities.HomeScreen;
import com.dbm.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_In_Fragment extends Fragment {

    View fragmentview;

    EditText email , password;
    TextView email_err , password_err, donthave_account;

    Button sign_up;

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentview = inflater.inflate(R.layout.fragment_sign_in, container, false);

        define_elements();
        login();

        return fragmentview;

    }

    private void define_elements() {

        email = fragmentview.findViewById(R.id.emailtxt);
        password = fragmentview.findViewById(R.id.passwordtxt);

        email_err = fragmentview.findViewById(R.id.email_err);
        password_err = fragmentview.findViewById(R.id.password_err);

        sign_up= fragmentview.findViewById(R.id.sign_up);

        donthave_account = fragmentview.findViewById(R.id.donthave_account);

        donthave_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(Sign_In_Fragment.this);
                navController.navigate(R.id.signUp_Fragment2);
            }
        });

    }


    private void login() {

        mAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getContext(), HomeScreen.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


            }
        });


    }


}