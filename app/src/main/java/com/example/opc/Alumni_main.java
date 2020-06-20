package com.example.opc;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.opc.ui.send.SendFragment;
import com.example.opc.ui.share.ShareFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Alumni_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_main);

        Toolbar toolbar = findViewById(R.id.alumni_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.alumni_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView=findViewById(R.id.alumni_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_alumni_discussion:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new ShareFragment()).commit();
                break;

            case R.id.nav_alumni_experience:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new Alumni_Discussion_Fragment()).commit();
                break;

            case R.id.nav_alumni_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new Alumni_Account_Fragment()).commit();
                break;

            case R.id.nav_alumni_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new SendFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed(){

            if(drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            }
            else {
                super.onBackPressed();
            }
        }


}
