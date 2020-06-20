package com.example.opc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailsTPO extends Fragment {

    String em,idfromem;
    TextView tname,tmail,tdate;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase rtdb;
    ListView l;
    ArrayAdapter<String> adapter;
    String[] default_items=new String[]{"Name","Email","Joining Date"};
    FirebaseUser user;
    List<String> itemList;
    Button ep,cp;
    EditText newpass;
    String newp;
    Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        context = getActivity();

        View v =  inflater.inflate(R.layout.activity_account_details_tpo, container, false);


        auth= FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            em=user.getEmail();
            int index=em.indexOf('@');
            idfromem=em.substring(0,index);
            Log.d("idfromemail",idfromem);
        }


        l=(ListView)v.findViewById(R.id.listview);
        ep=(Button)v.findViewById(R.id.editbtn);
        cp=(Button)v.findViewById(R.id.confirmbtn);
        newpass=(EditText)v.findViewById(R.id.enternew);
        cp.setVisibility(View.GONE);
        newpass.setVisibility(View.GONE);
        itemList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                String lname=dataSnapshot.child("tpo").child(idfromem).child("name").getValue(String.class);
                String lemail=dataSnapshot.child("tpo").child(idfromem).child("email").getValue(String.class);
                String ldate=dataSnapshot.child("tpo").child(idfromem).child("joining Date").getValue(String.class);

                itemList.add("TPO Name:"+lname);
                itemList.add("TPO Mail:" +lemail);
                itemList.add("Joining Date:" +ldate);

                adapter=new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,itemList);
                l.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Network error! Please check your connection",Toast.LENGTH_SHORT);
            }
        });

        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.setVisibility(View.VISIBLE);
                newpass.setVisibility(View.VISIBLE);

                changePassword();
            }
        });

    return v;
    }
    void changePassword(){

        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newp=newpass.getText().toString();
                if(user!=null ){
                    user.updatePassword(newp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"Your password has been changed",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(context,"Your password could not been changed",Toast.LENGTH_SHORT);

                            }
                        }
                    });
                }
            }
        });

    }
}

