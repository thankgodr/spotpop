package com.shixxels.thankgodrichard.spotpopfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.users.model.QBUser;
import com.shixxels.thankgodrichard.spotpopfinal.adapter.RecyclerAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.model.FeedAdapter;
import com.shixxels.thankgodrichard.spotpopfinal.segment.AddSpot;
import com.shixxels.thankgodrichard.spotpopfinal.segment.Feeds;
import com.shixxels.thankgodrichard.spotpopfinal.segment.Maplist;
import com.shixxels.thankgodrichard.spotpopfinal.segment.Notification;
import com.shixxels.thankgodrichard.spotpopfinal.segment.Profile;
import com.shixxels.thankgodrichard.spotpopfinal.segment.SpotAdd;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    Helpers app = Helpers.getInstance();
    QBUser user;

    ImageView feed_icon,serch_icon,add_icon,notify_icon,proofile_icon;
    View feed_line,search_line,add_line,notify_line,profile_line;
    LinearLayout linearLayout,feedsbtm,serchbtm,addbtm,notifictionbtm,profilebtm;
    public ImageView menuImg;
    public String[] loginDetails;
    public TextView notifyNo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        forceFullscreenOn();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize helper class for constant
        app.initQuickBlox(this);

        // Innitialize and cast all viewgrouups
        menuImg = (ImageView) findViewById(R.id.imgmenu);
        menuImg.setOnClickListener(this);
        notifyNo = (TextView) findViewById(R.id.nofityNumber);
        linearLayout = (LinearLayout) findViewById(R.id.container3);
        feedsbtm = (LinearLayout) findViewById(R.id.feedsbtm);
        serchbtm = (LinearLayout) findViewById(R.id.searchbtm);
        addbtm = (LinearLayout) findViewById(R.id.addbtm);
        notifictionbtm = (LinearLayout) findViewById(R.id.notificationbtm);
        profilebtm = (LinearLayout) findViewById(R.id.profilebtm);
        feed_line = findViewById(R.id.feed_line);
        search_line= findViewById(R.id.serch_line);
        add_line = findViewById(R.id.add_line);
        notify_line = findViewById(R.id.notify_line);
        profile_line = findViewById(R.id.profile_line);
        feed_icon = (ImageView) findViewById(R.id.feed_icon);
        serch_icon = (ImageView) findViewById(R.id.serch_icon);
        add_icon = (ImageView) findViewById(R.id.add_icon);
        notify_icon = (ImageView) findViewById(R.id.notify_icon);
        proofile_icon = (ImageView) findViewById(R.id.profile_icon);
        app.fetchSpots(this);
        //Set all Onclick listener
        feedsbtm.setOnClickListener(this);
        serchbtm.setOnClickListener(this);
        addbtm.setOnClickListener(this);
        notifictionbtm.setOnClickListener(this);
        profilebtm.setOnClickListener(this);
        //Initialize First Fragment
        Fragment  frag = new Feeds();
        linearLayout.removeAllViews();
        openFrag(frag,"feeds",R.id.container3);
        setBackground(1);









        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user = (QBUser) extras.get("info");
            loginDetails = (String[]) extras.get("login");
            SharedPreferences preference =getSharedPreferences(app.Pref, MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            Gson gson = new Gson();
            String login = gson.toJson(loginDetails,String[].class);
            String userJson = gson.toJson(user,QBUser.class);
            editor.putString("Spotpop",userJson);
            editor.putString("login",login);
            editor.commit();



        }
    }

    public void openFrag(Fragment frag, String map, int id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LinearLayout layout = (LinearLayout) findViewById(id);
        layout.removeAllViews();
        transaction.replace(R.id.container3,frag);
        transaction.addToBackStack(map);
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void forceFullscreenOn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    public void onClick(final View v) {

               Fragment frag = null;
                switch (v.getId()){
                    case R.id.feedsbtm:
                        frag = new Feeds();
                        openFrag(frag,"Feeds",R.id.container3);
                        setBackground(1);
                        Log.i(LoginActivity.TAG,"Feeds");
                        break;
                    case R.id.searchbtm:
                        frag = new Maplist();
                        openFrag(frag,"Maplist",R.id.container3);
                        setBackground(2);
                        Log.i(LoginActivity.TAG,"Serch");
                        break;
                    case R.id.addbtm:
                        frag = new SpotAdd();
                        openFrag(frag,"Spotadd",R.id.container3);
                        setBackground(3);
                        Log.i(LoginActivity.TAG,"add");
                        break;
                    case R.id.notificationbtm:
                        frag = new Notification();
                        openFrag(frag,"Notification",R.id.container3);
                        setBackground(4);
                        Log.i(LoginActivity.TAG,"notifi");
                        break;
                    case R.id.profilebtm:
                        frag = new Profile();
                        openFrag(frag,"Profile",R.id.container3);
                        setBackground(5);
                        Log.i(LoginActivity.TAG,"profile");
                        break;
                    case R.id.imgmenu:
                        Log.i("srr","menu in main Activity");
                }
    }

   public void setBackground(int btmsellected) {
            switch (btmsellected){
                case 1:
                    //handdles sellected view lines
                    feed_line.setVisibility(View.VISIBLE);
                    search_line.setVisibility(View.GONE);
                    add_line.setVisibility(View.GONE);
                    notify_line.setVisibility(View.GONE);
                    profile_line.setVisibility(View.GONE);

                    //Handles sellected View icons
                    feed_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.bgprofil));
                    serch_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    notify_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    proofile_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    break;
                case 2:
                    feed_line.setVisibility(View.GONE);
                    search_line.setVisibility(View.VISIBLE);
                    add_line.setVisibility(View.GONE);
                    notify_line.setVisibility(View.GONE);
                    profile_line.setVisibility(View.GONE);


                    feed_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    serch_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.bgprofil));
                    add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    notify_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    proofile_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));

                    break;
                case 3:
                    feed_line.setVisibility(View.GONE);
                    search_line.setVisibility(View.GONE);
                    add_line.setVisibility(View.VISIBLE);
                    notify_line.setVisibility(View.GONE);
                    profile_line.setVisibility(View.GONE);


                    feed_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    serch_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.bgprofil));
                    notify_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    proofile_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));

                    break;
                case 4:
                    feed_line.setVisibility(View.GONE);
                    search_line.setVisibility(View.GONE);
                    add_line.setVisibility(View.GONE);
                    notify_line.setVisibility(View.VISIBLE);
                    profile_line.setVisibility(View.GONE);


                    feed_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    serch_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    notify_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.bgprofil));
                    proofile_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));

                    break;
                case 5:
                    feed_line.setVisibility(View.GONE);
                    search_line.setVisibility(View.GONE);
                    add_line.setVisibility(View.GONE);
                    notify_line.setVisibility(View.GONE);
                    profile_line.setVisibility(View.VISIBLE);

                    feed_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    serch_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    notify_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.btmbartint));
                    proofile_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.bgprofil));

                    break;


            }

    }

    public void inboxRecall(View v){
        v.setOnClickListener(null);
        v.setOnClickListener(this);
    }
    public static Drawable setTint(Drawable drawable, int color) {
        final Drawable newDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("Low memory", "Too low");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
