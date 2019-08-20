package com.elsawy.ahmed.sqlaskproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.elsawy.ahmed.sqlaskproject.Activities.AboutActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.LoginActivity;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.FriendsFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.HomeFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.NotificationFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.ProfileFragment;
import com.elsawy.ahmed.sqlaskproject.Utils.Utilties;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    String TAG = "MainActivity";

    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    FriendsFragment friendsFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isLoggedIn())
            openLoginActivity();

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        friendsFragment = new FriendsFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    textView.setText("title_home");
                    showActionBar();
                    setFragment(homeFragment);
                    return true;

                case R.id.navigation_notifications:
//                    textView.setText("title_notifications");
                    showActionBar();
                    setFragment(notificationFragment);
                    return true;
                case R.id.navigation_friends:
//                    textView2.setText("title_friends");
                    showActionBar();
                    setFragment(friendsFragment);
                    return true;
                case R.id.navigation_profile:
//                    textView2.setText("title_profile");
                    hideActionBar();
                    setFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_setting) {
            Toast.makeText(MainActivity.this,"Setting",Toast.LENGTH_LONG).show();
            Log.i(TAG,"Setting Clicked");
        } else if (id == R.id.nav_about) {
            Toast.makeText(MainActivity.this,"About",Toast.LENGTH_LONG).show();
            Log.i(TAG,"About Clicked");

            startActivity(new Intent(MainActivity.this, AboutActivity.class));

        } else if (id == R.id.nav_logout) {
            Toast.makeText(MainActivity.this,"Log Out",Toast.LENGTH_LONG).show();
            Log.i(TAG,"Log Out Clicked");
            SharedPrefManager.getInstance(this).logout();
            openLoginActivity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void hideActionBar(){
        getSupportActionBar().hide();
    }

    public void showActionBar(){
        getSupportActionBar().show();
    }

}
