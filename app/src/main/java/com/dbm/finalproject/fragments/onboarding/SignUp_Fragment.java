package com.dbm.finalproject.fragments.onboarding;

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

import com.dbm.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUp_Fragment extends Fragment {

    View fragmentview;

    EditText email , password;
    TextView email_err , password_err, already;

    Button sign_up;

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentview = inflater.inflate(R.layout.onboarding_signup, container, false);

        define_ids();
        auth();

        return fragmentview;

    }

    private void define_ids() {

        email = fragmentview.findViewById(R.id.emailtxt);
        password = fragmentview.findViewById(R.id.passwordtxt);

        already = fragmentview.findViewById(R.id.already);

        email_err = fragmentview.findViewById(R.id.email_err);
        password_err = fragmentview.findViewById(R.id.password_err);

        sign_up= fragmentview.findViewById(R.id.sign_up);


    }


    private void auth() {

        mAuth = FirebaseAuth.getInstance();


        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(SignUp_Fragment.this);
                navController.navigate(R.id.sign_In_Fragment);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();

                                        NavController navController = NavHostFragment.findNavController(SignUp_Fragment.this);
                                        navController.navigate(R.id.personal_Details_Fragment);

                                    } else {
                                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }
        });


    }


}