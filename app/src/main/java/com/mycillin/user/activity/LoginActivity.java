package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginActivity_rl_loginLandingContainer)
    RelativeLayout loginLandingContainer;
    @BindView(R.id.loginActivity_rl_registerContainer)
    RelativeLayout registerContainer;
    @BindView(R.id.loginActivity_rl_loginContainer)
    RelativeLayout loginContainer;
    @BindView(R.id.loginActivity_rl_registerCompleteContainer)
    RelativeLayout registerCompleteContainer;

    @BindView(R.id.loginActivity_bt_showRegisterBtn)
    Button showRegisterBtn;
    @BindView(R.id.loginActivity_bt_showHaveAccBtn)
    Button showHaveAccBtn;
    @BindView(R.id.loginActivity_bt_registerBtn)
    Button doRegisterBtn;
    @BindView(R.id.loginActivity_bt_loginBtn)
    Button doLoginBtn;
    @BindView(R.id.loginActivity_bt_registerCompleteLoginBtn)
    Button doRegisterCompleteLoginBtn;

    @BindView(R.id.loginActivity_et_loginEmail)
    EditText emailEdtxt;
    @BindView(R.id.loginActivity_et_loginPassword)
    EditText passwordEdtxt;

    private boolean doubleBackToExitPressedOnce = false;
    private int MENU_FLAG_LANDING = 1001;
    private int MENU_FLAG_REGISTER = 1002;
    private int MENU_FLAG_LOGIN = 1003;
    private int MENU_FLAG_REGISTER_COMPLETE = 1004;
    private int MENU_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        MENU_FLAG = MENU_FLAG_LANDING;

        showRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterView();
            }
        });

        showHaveAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginView();
            }
        });

        registerFunction();
        loginFunction();
        registerCompleteFunction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void registerFunction() {
        doRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 13/09/2017 DO REGISTER
                toRegisterCompleteView();
            }
        });
    }

    public void loginFunction() {
        doLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 13/09/2017 DO LOGIN

                /*MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                HashMap<String, String> params = new HashMap<>();
                params.put("email", emailEdtxt.getText().toString());
                params.put("password", passwordEdtxt.getText().toString());

                final JSONObject jsonObject = new JSONObject(params);
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(Configs.RETROFIT_BASE_URL + "login/")
                        .post(body)
                        .addHeader("content-type", "application/json; charset=utf-8")
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                        Log.d("###", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull final okhttp3.Response response) throws IOException {
                        @SuppressWarnings("ConstantConditions")
                        final String result = response.body().string();
                        Log.d("###", "onResponse: " + result);
                    }
                });*/

                MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

                HashMap<String, String> params = new HashMap<>();
                params.put("email", emailEdtxt.getText().toString());
                params.put("password", passwordEdtxt.getText().toString());

                myCillinAPI.doLogin(params).enqueue(new retrofit2.Callback<ModelResultLogin>() {
                    @Override
                    public void onResponse(@NonNull retrofit2.Call<ModelResultLogin> call, @NonNull retrofit2.Response<ModelResultLogin> response) {

                        if(response.isSuccessful()) {
                            ModelResultLogin modelResultLogin = response.body();
                            assert modelResultLogin != null;

                            SessionManager session = new SessionManager(getApplicationContext());
                            session.createLoginSession(
                                    modelResultLogin.getResult().getData().getEmail(),
                                    modelResultLogin.getResult().getData().getFullName(),
                                    modelResultLogin.getResult().getData().getUserId(),
                                    modelResultLogin.getResult().getToken()
                            );

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getJSONObject("result").getString("message");
                                Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull retrofit2.Call<ModelResultLogin> call, @NonNull Throwable t) {
                        // TODO: 12/10/2017 SET FAILURE SCENARIO
                        Log.d("###", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    public void registerCompleteFunction() {
        doRegisterCompleteLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginView();
            }
        });
    }

    public void toLandingView(){
        loginLandingContainer.setVisibility(View.VISIBLE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_LANDING;
    }

    public void toRegisterView(){
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.VISIBLE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_REGISTER;
    }

    public void toLoginView(){
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.VISIBLE);
        registerCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_LOGIN;
    }

    public void toRegisterCompleteView(){
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.VISIBLE);

        MENU_FLAG = MENU_FLAG_REGISTER_COMPLETE;
    }

    @Override
    public void onBackPressed() {
        if(MENU_FLAG == MENU_FLAG_LANDING) {
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
        else {
            toLandingView();
        }
    }
}
