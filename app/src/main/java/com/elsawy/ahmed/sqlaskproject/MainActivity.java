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
import android.widget.Toast;

import com.elsawy.ahmed.sqlaskproject.Activities.AboutActivity;
import com.elsawy.ahmed.sqlaskproject.Activities.LoginActivity;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.FriendsFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.HomeFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.NotificationFragment;
import com.elsawy.ahmed.sqlaskproject.BottomFragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView ;
    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private FriendsFragment friendsFragment;
    private ProfileFragment profileFragment;

    private int currentBottomId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) //check user is login or not
            openLoginActivity();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        friendsFragment = new FriendsFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setFragment(Fragment newFragment){ // open the new fragment

        for (Fragment oldFragment : getSupportFragmentManager().getFragments()) {  // close all old fragment in the container before open new fragment
            getSupportFragmentManager().beginTransaction().remove(oldFragment).commit();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, newFragment);
        fragmentTransaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // handle Bottom Navigation  buttons

            if (currentBottomId == item.getItemId()
                    || (currentBottomId == -1 && item.getItemId() == R.id.navigation_home)) // check this fragment is opened or not to not open it again
                return false;

            currentBottomId = item.getItemId();

            switch (item.getItemId()) {
                case R.id.navigation_home:   // open home Bottom
                    showActionBar();
                    setFragment(homeFragment);
                    return true;

                case R.id.navigation_notifications:  // open notifications Bottom
                    showActionBar();
                    setFragment(notificationFragment);
                    return true;
                case R.id.navigation_friends:   // open friends Bottom
                    showActionBar();
                    setFragment(friendsFragment);
                    return true;
                case R.id.navigation_profile:   // open profile Bottom
                    hideActionBar();
                    setFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { // close navigationDrawer if it is opened
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {  // handle navigation drawer buttons

        switch(menuItem.getItemId()){

            case R.id.nav_setting:
                Toast.makeText(MainActivity.this,"Setting",Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_about:
                Toast.makeText(MainActivity.this,"About",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

            case R.id.nav_logout:
                Toast.makeText(MainActivity.this,"Log Out",Toast.LENGTH_LONG).show();
                SharedPrefManager.getInstance(this).logout();
                openLoginActivity();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);  // close DrawerLayout after click buttons
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
