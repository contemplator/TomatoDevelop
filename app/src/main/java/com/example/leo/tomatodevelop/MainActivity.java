package com.example.leo.tomatodevelop;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import animation.CircleAngleAnimation;
import view.Circle;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String ACTIVITY_NAME = "MainActivity";
    private Circle circle;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_next;
    private int surplus_second = 0;
    private CountDownTimer clock;
    TextView txt_minute;
    TextView txt_second;

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

        initView();
    }

    private void initView(){
        Circle circle_bg = (Circle) findViewById(R.id.circle＿bg);
        circle_bg.setColor(30, 0, 0, 0);
        circle_bg.setAngle(360);

        circle = (Circle) findViewById(R.id.circle);
        circle.setAngle(0);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener((View.OnClickListener) this);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener((View.OnClickListener) this);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener((View.OnClickListener) this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener((View.OnClickListener) this);

        txt_minute = (TextView) findViewById(R.id.txt_minute);
        txt_second = (TextView) findViewById(R.id.txt_second);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

    public void startTick() {
        // get minutes and second init time
        int minutes = Integer.parseInt(txt_minute.getText().toString());
        int seconds = Integer.parseInt(txt_second.getText().toString());
        surplus_second = minutes*60 + seconds;

        // update btn status
        btn_start.setVisibility(View.GONE);
        btn_pause.setVisibility(View.VISIBLE);
        btn_stop.setEnabled(true);

        if(clock != null){
            clock.cancel();
            clock = null;
        }else{
            clock = new CountDownTimer((int)surplus_second*1000, 1000) {
                double degree = 0;
                double degreeStep = 360/surplus_second;

                @Override
                public void onTick(long millisUntilFinished) {
                    // count down nunmber
                    if(Integer.parseInt(txt_second.getText().toString()) == 0){
                        txt_minute.setText(Integer.parseInt(txt_minute.getText().toString())-1+"");
                        txt_second.setText("59");
                    }else{
                        txt_second.setText(Integer.parseInt(txt_second.getText().toString())-1+"");
                    }

                    // draw circle animation
                    degree += degreeStep;
                    CircleAngleAnimation animation = new CircleAngleAnimation(circle, Double.valueOf(degree).intValue());
                    animation.setDuration(100);
                    circle.startAnimation(animation);
                }

                @Override
                public void onFinish() {
                    // finish view
                    txt_minute.setText("0");
                    txt_second.setText("0");
                    CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
                    animation.setDuration(100);
                    circle.startAnimation(animation);

                    // show alarm and next step
                    btn_stop.setVisibility(View.GONE);
                    btn_start.setVisibility(View.GONE);
                    btn_pause.setVisibility(View.GONE);

                }
            }.start();
        }
    }

    public void pauseTick(){
        clock.cancel();
        clock = null;
        btn_start.setVisibility(View.VISIBLE);
        btn_pause.setVisibility(View.GONE);
        btn_stop.setEnabled(false);
    }

    public void stopTick(){
        clock.cancel();
        clock = null;
        txt_minute.setText("04");
        txt_second.setText("00");
        btn_start.setVisibility(View.VISIBLE);
        btn_stop.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_start:
                startTick();
                break;
            case R.id.btn_stop:
                stopTick();
                break;
            case R.id.btn_pause:
                pauseTick();
                break;
            case R.id.btn_next:
                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, TestExam002.class);
                startActivity(intent);
            default:
                break;

        }
    }
}
