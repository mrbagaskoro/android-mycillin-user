package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.fragment.AboutFragment;
import com.mycillin.user.fragment.EWalletFragment;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.fragment.MedicalRecordFragment;

public class MainActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_home:
                    tx.replace(R.id.mainActivity_fl_framecontainer, new HomeFragment());
                    tx.commit();
                    getSupportActionBar().setTitle(R.string.app_name);

                    return true;
                case R.id.nav_medical_record:
                    tx.replace(R.id.mainActivity_fl_framecontainer, new MedicalRecordFragment());
                    tx.commit();
                    getSupportActionBar().setTitle(R.string.nav_medical_record);

                    return true;
                case R.id.nav_wallet:
                    tx.replace(R.id.mainActivity_fl_framecontainer, new EWalletFragment());
                    tx.commit();
                    getSupportActionBar().setTitle(R.string.nav_e_wallet);

                    return true;
                case R.id.nav_about:
                    tx.replace(R.id.mainActivity_fl_framecontainer, new AboutFragment());
                    tx.commit();
                    getSupportActionBar().setTitle(R.string.nav_about_mycillin);

                    return true;
            }

            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // -------------------------------------------------------------------------------------- //
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mainActivity_fl_framecontainer, new HomeFragment());
        tx.commit();
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.pressBackAgainToLeave, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_account) {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.action_invite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
