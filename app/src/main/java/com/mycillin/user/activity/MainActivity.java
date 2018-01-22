package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.firebase.FirebaseManager;
import com.mycillin.user.fragment.AboutFragment;
import com.mycillin.user.fragment.EWalletFragment;
import com.mycillin.user.fragment.HistoryFragment;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.fragment.MedicalRecordFragment;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.unratedList.ModelResultUnratedList;
import com.mycillin.user.util.ApplicationPreferencesManager;
import com.mycillin.user.util.BottomNavigationViewHelper;
import com.mycillin.user.util.Configs;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private ProgressBarHandler progressBarHandler;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        progressBarHandler = new ProgressBarHandler(this);

        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        /*tx.replace(R.id.mainActivity_fl_framecontainer, new EWalletFragment());
                        tx.commit();
                        getSupportActionBar().setTitle(R.string.nav_e_wallet);*/

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.mainActivity_infoTitle))
                                .setCancelable(false)
                                .setMessage(R.string.mainActivity_comingSoonMessage)
                                .setIcon(R.mipmap.ic_launcher)
                                .setPositiveButton(getString(R.string.ratingActivity_ratingDesc3), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        navigation.setSelectedItemId(R.id.nav_home);
                                    }
                                })
                                .show();

                        return true;
                    case R.id.nav_about:
                        tx.replace(R.id.mainActivity_fl_framecontainer, new AboutFragment());
                        tx.commit();
                        getSupportActionBar().setTitle(R.string.nav_about_mycillin);

                        return true;
                }

                return false;
            }
        });

        ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        if (applicationPreferencesManager.isFirstLaunched()) {
            Intent intent = new Intent(MainActivity.this, BigBannerActivity.class);
            startActivity(intent);
        }

        sendTokenFirebase();
        navigation.setSelectedItemId(R.id.nav_home);
    }

    private void sendTokenFirebase() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        FirebaseManager firebaseManager = new FirebaseManager(getApplicationContext());
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", sessionManager.getUserId());
        params.put("token", firebaseManager.getFirebaseToken());

        JSONObject jsonObject = new JSONObject(params);
        Log.d("#8#8#", "sendTokenFirebase: " + params);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(Configs.RETROFIT_BASE_URL + "token_fcm_patient/")
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("Authorization", sessionManager.getUserToken())
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                String result = response.body().string();
                Log.d("#8#8#", "onResponse: " + result);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("result")) {
                            boolean status = jsonObject.getJSONObject("result").getBoolean("status");
                            if (status) {
                                Log.d("#8#8#", "onResponse: SIP");
                            } else {
                                String message = jsonObject.getJSONObject("result").getString("message");
                                Log.d("#8#8#", "onResponse: SIP" + message);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {
                            message = jsonObject.getString("message");
                        }

                        Log.d("#8#8#", "onResponse: gagal" + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (navigation.getSelectedItemId() == R.id.nav_home) {
            getUnratedList();
        }
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
        } else if (id == R.id.action_invite) {
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

                if (response.isSuccessful()) {
                    ModelResultUnratedList modelResultUnratedList = response.body();

                    assert modelResultUnratedList != null;
                    if (modelResultUnratedList.getResult().isStatus()) {
                        int size = modelResultUnratedList.getResult().getData().size();
                        for (int i = 0; i < size; i++) {
                            Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                            intent.putExtra(RatingActivity.EXTRA_PARAM_CREATED_DATE, modelResultUnratedList.getResult().getData().get(i).getCreatedDate());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_BOOKING_ID, modelResultUnratedList.getResult().getData().get(i).getBookingId());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_PARTNER_ID, modelResultUnratedList.getResult().getData().get(i).getPartnerSelected());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_PARTNER_PIC, modelResultUnratedList.getResult().getData().get(i).getPartnerPhoto());
                            intent.putExtra(RatingActivity.EXTRA_PARAM_PARTNER_NAME, modelResultUnratedList.getResult().getData().get(i).getFullName());
                            startActivity(intent);
                        }
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

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
