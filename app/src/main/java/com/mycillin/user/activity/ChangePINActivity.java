package com.mycillin.user.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.pinSet.ModelResultPinSet;
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

public class ChangePINActivity extends AppCompatActivity {

    @BindView(R.id.changePINActivity_et_password)
    EditText passwordEdtxt;
    @BindView(R.id.changePINActivity_et_PIN)
    EditText PINEdtxt;
    @BindView(R.id.changePINActivity_et_confirmPIN)
    EditText confirmPINEdtxt;

    @BindView(R.id.changePINActivity_toolbar)
    Toolbar toolbar;

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.changePINActivity_title);

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
            if(passwordEdtxt.getText().toString().trim().equals("")) {
                passwordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                isValid = false;
            }
            if(PINEdtxt.getText().toString().trim().equals("")) {
                PINEdtxt.setError(getString(R.string.changePINActivity_PINWarning));
                isValid = false;
            }
            if(confirmPINEdtxt.getText().toString().trim().equals("")) {
                confirmPINEdtxt.setError(getString(R.string.changePINActivity_PINConfirmationWarning));
                isValid = false;
            }
            if(!confirmPINEdtxt.getText().toString().trim().equals(PINEdtxt.getText().toString().trim())) {
                confirmPINEdtxt.setError(getString(R.string.changePINActivity_PINMatchWarning));
                isValid = false;
            }
            if(PINEdtxt.getText().toString().trim().length() != 4) {
                PINEdtxt.setError(getString(R.string.changePINActivity_PINLengthWarning));
                isValid = false;
            }
            if(confirmPINEdtxt.getText().toString().trim().length() != 4) {
                confirmPINEdtxt.setError(getString(R.string.changePINActivity_PINConfirmationLengthWarning));
                isValid = false;
            }

            if(isValid) {
                setPIN(passwordEdtxt.getText().toString().trim(), confirmPINEdtxt.getText().toString().trim());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setPIN(String password, String pin) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("password", password);
        params.put("pin", pin);

        myCillinAPI.setPin(token, params).enqueue(new Callback<ModelResultPinSet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPinSet> call, @NonNull Response<ModelResultPinSet> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultPinSet modelResultPinSet = response.body();

                    assert modelResultPinSet != null;
                    if(modelResultPinSet.getResult().isStatus()) {
                        String message = modelResultPinSet.getResult().getMessage();
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
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
            public void onFailure(@NonNull Call<ModelResultPinSet> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
