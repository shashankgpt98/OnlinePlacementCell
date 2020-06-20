package com.example.opc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private RadioButton adminRB,tpoRB,studentRB,alumniRB;
    private Button SignIn;
    private  String myEmail,myPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference();
//    private DatabaseReference adminrref = myRef.child("admin");
//    private DatabaseReference tporef = myRef.child("tpo");
//    private DatabaseReference studentref = myRef.child("student");
    @Override
    public void onStart() {
        super.onStart();
//        updateUI();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        adminRB = findViewById(R.id.admin);
        studentRB = findViewById(R.id.student);
        alumniRB = findViewById(R.id.alumni);
        tpoRB = findViewById(R.id.tpo);
        SignIn = findViewById(R.id.signIn);
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null) {
//            Log.d("user email", currentUser.getEmail());
//            Intent intent = new Intent(MainActivity.this,student_main.class);
//            startActivity(intent);
//        }
//
//        else
//            Log.i("user email","null");

        updateUI();
    }

    private void updateUI() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            String myEmail = currentUser.getEmail();
            int index = myEmail.indexOf('@');
            String node = myEmail.substring(0,index);
            myRef.child("admin").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,adminActivity1.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myRef.child("student").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,student_main.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            myRef.child("Alumni").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,Alumni_main.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myRef.child("tpo").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,TpoActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else
            Log.i("user email","null");
    }

    public void checkAdmin(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("admin").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, adminActivity1.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Admin Account Doesnt Exist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkTpo(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);

        myRef.child("tpo").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, TpoActivity.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Tpo Account Doesn't Exist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkStudent(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("student").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, student_main.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Student Account Doesn't Exist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void checkAlumni(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("Alumni").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, Alumni_main.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Alumni Account Doesn't Exist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void signIn(View view) {
        myEmail = email.getText().toString().trim();
        myPassword = password.getText().toString().trim();

        if (myEmail.length() <= 0 || myPassword.length() <= 0)
            Toast.makeText(MainActivity.this, "please complete all the fields", Toast.LENGTH_SHORT).show();
        else{
                if(adminRB.isChecked()){
                    checkAdmin();
                }
                else if(tpoRB.isChecked()){
                    checkTpo();
                }
                else if(studentRB.isChecked()){
                    checkStudent();
                }
                else if(alumniRB.isChecked()){
                    checkAlumni();
                }
                else
                    Toast.makeText(MainActivity.this,"Opt One Of The Choice",Toast.LENGTH_SHORT).show();
            }
    }
}
