package com.example.opc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opc.ui.gallery.GalleryViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Alumni_Account_Fragment extends Fragment {
    String em,idfromem;
    TextView tname,tid,tmail,tbranch;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase rtdb;
    ListView l;
    ArrayAdapter<String> adapter;
    String[] default_items=new String[]{"Name","Email","Id","Branch","Pass_Out_Year","Current"};
    FirebaseUser user;
    List<String> itemList;
    Button ep,cp;
    EditText newpass;
    String newp;
    private GalleryViewModel galleryViewModel;
    Activity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        context.setTitle("Account Details");

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        auth= FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            em=user.getEmail();
            int index=em.indexOf('@');
            idfromem=em.substring(0,index);
            Log.d("idfromemail",idfromem);
        }
        l=(ListView)root.findViewById(R.id.listview);
        ep=(Button)root.findViewById(R.id.editbtn);
        cp=(Button)root.findViewById(R.id.confirmbtn);
        newpass=(EditText)root.findViewById(R.id.enternew);
        cp.setVisibility(View.GONE);
        newpass.setVisibility(View.GONE);
        itemList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                String lname=dataSnapshot.child("Alumni").child(idfromem).child("name").getValue(String.class);
                String lemail=dataSnapshot.child("Alumni").child(idfromem).child("email").getValue(String.class);
                String lbranch=dataSnapshot.child("Alumni").child(idfromem).child("branch").getValue(String.class);
                String lid=dataSnapshot.child("Alumni").child(idfromem).child("id").getValue(String.class);
                String lpassyear=dataSnapshot.child("Alumni").child(idfromem).child("passyear").getValue(String.class);
                String lcurrent=dataSnapshot.child("Alumni").child(idfromem).child("current").getValue(String.class);

                itemList.add("Alumni Name : "+lname);
                itemList.add("Institute Mail : " +lemail);
                itemList.add("Branch : " +lbranch);
                itemList.add("Institute I.D. : "+lid);
                itemList.add("Pass Out Year : "+lpassyear);
                itemList.add("Current . : "+lcurrent);

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

        return root;
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
                                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                                fAuth.signOut();
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
