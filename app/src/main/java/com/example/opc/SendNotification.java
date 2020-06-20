package com.example.opc;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


public class SendNotification extends Fragment {

    private EditText message,recId;
    private String key1,key2,key3,key4;
    private int p;
    private Button sendNotif,sendToAll;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootref = db.getReference();
    private DatabaseReference userref = rootref.child("notification");
    private DatabaseReference studentref = rootref.child("student");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_send_notification, container, false);

        message = v.findViewById(R.id.notifMessage);
        recId = v.findViewById(R.id.receiversId);
        sendNotif = v.findViewById(R.id.sendNotification);
        sendToAll = v.findViewById(R.id.sendToAll);
        sendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotifToAll();
            }
        });
        sendNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });



        return v;
    }

    public void sendNotifToAll(){
        String msg = message.getText().toString();
        String iid = recId.getText().toString();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        final HashMap<String,String> map = new HashMap<String, String>();


        if(iid.length()>0)
            Toast.makeText(getActivity(),"please remove the entered id",Toast.LENGTH_SHORT).show();
        else if(msg.length()<=0)
            Toast.makeText(getActivity(), "empty message!", Toast.LENGTH_SHORT).show();
        else {
            map.put("notif",msg);
            map.put("date",currentDate);
            map.put("time",currentTime);

            studentref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        key1 = (String) ((DataSnapshot) i.next()).getValue();
                        key2 = (String) ((DataSnapshot) i.next()).getValue();
                        key3 = (String) ((DataSnapshot) i.next()).getValue();
                        key4 = (String) ((DataSnapshot) i.next()).getValue();

//                        Log.d("TAG", key);

                        userref.child(key3).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    p = -1;
                                }
                            }
                        });

                    }
//                    if (p != -1)
//                        Toast.makeText(getActivity(), "Notification Sent To All", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public void sendNotification(){
        String msg = message.getText().toString();
        String id = recId.getText().toString().trim();

        if(id.length()<=0)
            Toast.makeText(getActivity(), "Id should Not be Empty", Toast.LENGTH_SHORT).show();
        if(msg.length()<=0)
            Toast.makeText(getActivity(), "Plaese Enter some message", Toast.LENGTH_SHORT).show();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        HashMap<String,String> map = new HashMap<String, String>();
        map.put("notif",msg);
        map.put("date",currentDate);
        map.put("time",currentTime);

        userref.child(id).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(getActivity(), "Notification Sent", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }



}
