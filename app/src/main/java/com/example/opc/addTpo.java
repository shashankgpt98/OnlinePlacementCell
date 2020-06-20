package com.example.opc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class addTpo extends Fragment {

    private EditText name,email,joiningDate,password;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootref = db.getReference();
    private DatabaseReference userref = rootref.child("tpo");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v =  inflater.inflate(R.layout.fragment_add_tpo, container, false);

        name = v.findViewById(R.id.addTpoName);
        email = v.findViewById(R.id.addtpoEmail);
        joiningDate = v.findViewById(R.id.addTpoJd);
        password = v.findViewById(R.id.addtpoPass);
        register = v.findViewById(R.id.addregisterTPO);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTpoAccount();
                addTpoInDatabase();
            }
        });

        return v;
    }

    public void createTpoAccount(){
        final String myEmail = email.getText().toString();
        final String myPassword = password.getText().toString();
        if(myEmail.length()<=0 || myPassword.length()<=0)
            Toast.makeText(getContext(),"please fill required fields",Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "createUserWithEmail:success");
                            Toast.makeText(getActivity(),"Sign Up Successful",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Sign Up failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void addTpoInDatabase(){
        final String myEmail = email.getText().toString().trim();
        final String joinDate = joiningDate.getText().toString().trim();
        final String myname = name.getText().toString().trim();

        int index = myEmail.indexOf('@');
        String dbIdOftpo = myEmail.substring(0,index);

        if(myEmail.length()<=0 || joinDate.length()<=0 || myname.length()<=0)
            Toast.makeText(getActivity(),"please fill required fields",Toast.LENGTH_SHORT).show();

        HashMap<String,String > tpoMap = new HashMap<String, String>();
        tpoMap.put("name",myname);
        tpoMap.put("email",myEmail);
        tpoMap.put("joining Date",joinDate);

        userref.child(dbIdOftpo).setValue(tpoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                        Toast.makeText(getActivity(),"Registration Successfull",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
