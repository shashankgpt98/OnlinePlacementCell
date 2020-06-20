package com.example.opc;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opc.ui.share.ShareViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Alumni_Discussion_Fragment extends Fragment {
    Button sendbtn;
    EditText input_msg;
    TextView chat_conv;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference root;
    String em,chat_msg,chat_user_name;
    String text;
    private ShareViewModel shareViewModel;
    Activity context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context=getActivity();
        context.setTitle("Discussion");

        View v = inflater.inflate(R.layout.fragment_alumni_discussion, container, false);
        auth= FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            em = user.getEmail();
            Log.i("email",em);
        }
        sendbtn= (Button) v.findViewById(R.id.btn_send);
        input_msg=(EditText) v.findViewById(R.id.msg_input);

        chat_conv=(TextView) v.findViewById(R.id.textView);
        root= FirebaseDatabase.getInstance().getReference().child("experience");
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text=input_msg.getText().toString();
                HashMap<String, String> map2=new HashMap<String, String>();
                map2.put("msg",text);
                map2.put("name",em);
                Log.i("info",text+"By"+em);
                //message_root.updateChildren(map2);
                root.push().setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"success",Toast.LENGTH_SHORT).show();
                        //input_msg.setText("");
                    }
                });
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat(dataSnapshot);
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

        return v;
    }
    private void append_chat(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {

            chat_msg =
                    (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name =
                    (String) ((DataSnapshot) i.next()).getValue();

            Log.i("iii",chat_msg+""+chat_user_name);

            //chat_conv.append(chat_user_name+"\n"+chat_msg+"\n");
            chat_conv.append(Html.fromHtml("<html><body><font size='1' color=blue><small><small>"+chat_user_name+" </small></small></font> <br/>"+chat_msg+"<br/><br/> </body><html>"));

        }
    }
}