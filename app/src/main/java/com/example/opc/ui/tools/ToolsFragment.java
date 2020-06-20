package com.example.opc.ui.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.opc.R;
import com.example.opc.company_details;
import com.example.opc.uploadJAF;
import com.example.opc.view_company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ToolsFragment extends Fragment {
    private ToolsViewModel toolsViewModel;
    Activity context;
    ListView mycomView;
    DatabaseReference databaseReference;
    List<uploadJAF> jafs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        context.setTitle("Upcoming Companies");

        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        mycomView=(ListView)root.findViewById(R.id.clist);
        jafs=new ArrayList<>();

        viewAllCom();

        mycomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                uploadJAF j = jafs.get(i);
                Gson gson=new Gson();
                String myJson = gson.toJson(j);
                Intent intent=new Intent(context, company_details.class);
                intent.putExtra("myjson",myJson);
                startActivity(intent);

            }
        });
        return root;
    }

    private void viewAllCom(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Company");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    uploadJAF JAF = postSnapshot.getValue(com.example.opc.uploadJAF.class);
                    jafs.add(JAF);
                }
                String[] comps=new String[jafs.size()];
                for(int i=0;i<comps.length;i++){
                    comps[i]=jafs.get(i).getName();
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,comps);
                mycomView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

