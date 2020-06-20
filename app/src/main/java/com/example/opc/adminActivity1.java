package com.example.opc;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.opc.ui.send.SendFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class adminActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer1;
    private TextView navEmailDisp,username_navheader;;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);

        Toolbar toolbar1 = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar1);
        drawer1 = findViewById(R.id.admin_drawer_layout);
        mAuth = FirebaseAuth.getInstance();

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        navEmailDisp = header.findViewById(R.id.navheaderemail);
        username_navheader = header.findViewById(R.id.nav_header_username);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer1,toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer1.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new addTpo()).commit();
        navigationView.setCheckedItem(R.id.add_tpo_symbol);

        FirebaseUser currentUser = mAuth.getCurrentUser();
            String Useremail = currentUser.getEmail();
              int index = Useremail.indexOf('@');
              String node = Useremail.substring(0,index);


        username_navheader.setText(node);


        Log.d("check",Useremail);
            navEmailDisp.setText(Useremail);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.acc_detail_tpo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new addTpo()).commit();
                break;
            case R.id.add_student_symbol:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new AddStudentFrag()).commit();
                break;
            case R.id.add_tpo_symbol:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new addTpo()).commit();
                 break;
            case R.id.log_out_symbol:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new SendFragment()).commit();
                break;
        }

        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer1.isDrawerOpen(GravityCompat.START)){
            drawer1.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }

    }
}
