package com.mycillin.user.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.changePassword.ModelResultChangePassword;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.changePasswordActivity_et_oldPassword)
    EditText oldPasswordEdtxt;
    @BindView(R.id.changePasswordActivity_et_newPassword)
    EditText newPasswordEdtxt;
    @BindView(R.id.changePasswordActivity_et_confirmNewPassword)
    EditText confirmNewPasswordEdtxt;

    @BindView(R.id.changePasswordActivity_toolbar)
    Toolbar toolbar;

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.changePasswordDialog_title);

        progressBarHandler = new ProgressBarHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            boolean isValid = true;
            if(oldPasswordEdtxt.getText().toString().trim().equals("")) {
                oldPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                isValid = false;
            }
            if(newPasswordEdtxt.getText().toString().trim().equals("")) {
                newPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                isValid = false;
            }
            if(confirmNewPasswordEdtxt.getText().toString().trim().equals("")) {
                confirmNewPasswordEdtxt.setError(getString(R.string.loginActivity_passwordConfirmationWarning));
                isValid = false;
            }
            if(!confirmNewPasswordEdtxt.getText().toString().trim().equals(newPasswordEdtxt.getText().toString().trim())) {
                confirmNewPasswordEdtxt.setError(getString(R.string.loginActivity_passwordMatchWarning));
                isValid = false;
            }

            if(isValid) {
                doChangePassword(oldPasswordEdtxt.getText().toString(), newPasswordEdtxt.getText().toString());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void doChangePassword(String oldPassword, String newPassword) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("old_password", oldPassword);
        params.put("new_password", newPassword);

        myCillinAPI.doChangePassword(token, params).enqueue(new Callback<ModelResultChangePassword>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultChangePassword> call, @NonNull Response<ModelResultChangePassword> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultChangePassword modelResultChangePassword = response.body();

                    assert modelResultChangePassword != null;
                    if(modelResultChangePassword.getResult().isStatus()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultChangePassword.getResult().getMessage(), Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>(){
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);

                                        SessionManager session = new SessionManager(getApplicationContext());
                                        session.logoutUser();
                                        finish();
                                    }
                                })
                                .show();
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
            public void onFailure(@NonNull Call<ModelResultChangePassword> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}
