package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.fragment.AboutFragment;
import com.mycillin.user.fragment.EWalletFragment;
import com.mycillin.user.fragment.HistoryFragment;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.fragment.MedicalRecordFragment;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.unratedList.ModelResultUnratedList;
import com.mycillin.user.util.BottomNavigationViewHelper;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBarHandler progressBarHandler;
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
                case R.id.nav_history:
                    tx.replace(R.id.mainActivity_fl_framecontainer, new HistoryFragment());
                    tx.commit();
                    getSupportActionBar().setTitle(R.string.nav_history);

                    return true;
                case R.id.nav_medical_record:

                    Intent intent = new Intent(MainActivity.this, PINActivity.class);
                    startActivity(intent);

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

        progressBarHandler = new ProgressBarHandler(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // -------------------------------------------------------------------------------------- //
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mainActivity_fl_framecontainer, new HomeFragment());
        tx.commit();
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUnratedList();
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
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String userId = sessionManager.getUserId();
            String link = " http://mycillin.com/services/web/registration/" + userId;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareIntent_subject));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareIntent_text) + link);
            startActivity(Intent.createChooser(intent, getString(R.string.shareIntent_title)));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUnratedList() {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        myCillinAPI.getUnratedList(token, params).enqueue(new Callback<ModelResultUnratedList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultUnratedList> call, @NonNull Response<ModelResultUnratedList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultUnratedList modelResultUnratedList = response.body();

                    assert modelResultUnratedList != null;
                    if(modelResultUnratedList.getResult().isStatus()) {
                        int size = modelResultUnratedList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                            intent.putExtra(RatingActivity.EXTRA_PARAM_CREATED_DATE, modelResultUnratedList.getResult().getData().get(i).getCreatedDate());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_BOOKING_ID, modelResultUnratedList.getResult().getData().get(i).getBookingId());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_PARTNER_ID, modelResultUnratedList.getResult().getData().get(i).getPartnerSelected());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_PARTNER_NAME, modelResultUnratedList.getResult().getData().get(i).getFullName());
                            startActivity(intent);
                        }
                    }
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if(jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        }
                        else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultUnratedList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("###", "onFailurex: " + t.getMessage());
                Log.d("###", "onFailurexx: " + t.getLocalizedMessage());
            }
        });
    }
}
