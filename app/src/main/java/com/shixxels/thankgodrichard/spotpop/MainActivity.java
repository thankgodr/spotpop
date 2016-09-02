package com.shixxels.thankgodrichard.spotpop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





    }

    // Fragment use this to change the Title at the app bar
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.myAccout) {
            new OpenFragmentLayout().Account();
        } else if (id == R.id.mySpot) {
            new OpenFragmentLayout().MySpot();

        } else if (id == R.id.myFav) {
            new OpenFragmentLayout().MyFav();

        } else if (id == R.id.spotList) {
            new OpenFragmentLayout().SpotList();

        } else if (id == R.id.mySpot) {
            new OpenFragmentLayout().MySpot();


        } else if (id == R.id.spotMap) {
            new OpenFragmentLayout().SpotMap();


        }else if (id == R.id.about) {
            new OpenFragmentLayout().About();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class OpenFragmentLayout {
        private void About() {
            About fragment = new About();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
        }
        private void MyFav() {
            MyFav fragment = new MyFav();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
        private void MySpot() {
            MySpot fragment = new MySpot();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
        private void Account() {
            account fragment = new account();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
        private void SpotMap() {
            SpotMap fragment = new SpotMap();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
        private void SpotList() {
            SpotList fragment = new SpotList();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }
    }

}
