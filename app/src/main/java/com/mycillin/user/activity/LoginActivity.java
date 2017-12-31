package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountPicGet.ModelResultAccountPicGet;
import com.mycillin.user.rest.facebookLogin.ModelResultFacebookLogin;
import com.mycillin.user.rest.forgotPassword.ModelResultForgotPassword;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.rest.register.ModelResultRegister;
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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginActivity_rl_loginLandingContainer)
    RelativeLayout loginLandingContainer;
    @BindView(R.id.loginActivity_rl_registerContainer)
    RelativeLayout registerContainer;
    @BindView(R.id.loginActivity_rl_loginContainer)
    RelativeLayout loginContainer;
    @BindView(R.id.loginActivity_rl_registerCompleteContainer)
    RelativeLayout registerCompleteContainer;
    @BindView(R.id.loginActivity_rl_forgotPasswordContainer)
    RelativeLayout forgotPasswordContainer;
    @BindView(R.id.loginActivity_rl_forgotPasswordCompleteContainer)
    RelativeLayout forgotPasswordCompleteContainer;

    @BindView(R.id.loginActivity_bt_showRegisterBtn)
    Button showRegisterBtn;
    @BindView(R.id.loginActivity_bt_showHaveAccBtn)
    Button showHaveAccBtn;
    @BindView(R.id.loginActivity_tv_forgotPassword)
    TextView showForgotPasswordBtn;
    @BindView(R.id.loginActivity_bt_registerBtn)
    Button doRegisterBtn;
    @BindView(R.id.loginActivity_bt_loginBtn)
    Button doLoginBtn;
    @BindView(R.id.loginActivity_bt_registerCompleteLoginBtn)
    Button doRegisterCompleteLoginBtn;
    @BindView(R.id.loginActivity_bt_forgotPasswordBtn)
    Button doForgotPasswordBtn;
    @BindView(R.id.loginActivity_bt_forgotPasswordCompleteLoginBtn)
    Button doForgotPasswordCompleteBtn;
    @BindView(R.id.loginActivity_bt_fbLoginBtn)
    LoginButton fbLoginBtn;

    @BindView(R.id.loginActivity_et_loginEmail)
    EditText loginEmailEdtxt;
    @BindView(R.id.loginActivity_et_loginPassword)
    EditText loginPasswordEdtxt;

    @BindView(R.id.loginActivity_et_registerEmail)
    EditText registerEmailEdtxt;
    @BindView(R.id.loginActivity_et_registerPassword)
    EditText registerPasswordEdtxt;
    @BindView(R.id.loginActivity_et_registerConfirmPassword)
    EditText registerConfirmPasswordEdtxt;
    @BindView(R.id.loginActivity_et_registerName)
    EditText registerNameEdtxt;

    @BindView(R.id.loginActivity_et_forgotPasswordEmail)
    EditText forgotPasswordEmailEdtxt;

    private boolean doubleBackToExitPressedOnce = false;
    private int MENU_FLAG_LANDING = 1001;
    private int MENU_FLAG_REGISTER = 1002;
    private int MENU_FLAG_LOGIN = 1003;
    private int MENU_FLAG_REGISTER_COMPLETE = 1004;
    private int MENU_FLAG_FORGOT_PASSWORD = 1005;
    private int MENU_FLAG_FORGOT_PASSWORD_COMPLETE = 1006;
    private int MENU_FLAG;

    CallbackManager callbackManager;
    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();

        MENU_FLAG = MENU_FLAG_LANDING;

        progressBarHandler = new ProgressBarHandler(this);

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

        showForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toForgotPasswordView();
            }
        });


        fbLoginBtn.setReadPermissions("email");
        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFacebookUserDetails(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        registerFunction();
        loginFunction();
        registerCompleteFunction();
        forgotPasswordFunction();
        forgotPasswordCompleteFunction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        else {
            LoginManager.getInstance().logOut();
        }
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

    private void registerFunction() {
        doRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if(registerEmailEdtxt.getText().toString().trim().equals("")) {
                    registerEmailEdtxt.setError(getString(R.string.loginActivity_emailWarning));
                    isValid = false;
                }
                if(registerPasswordEdtxt.getText().toString().trim().equals("")) {
                    registerPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                    isValid = false;
                }
                if(registerConfirmPasswordEdtxt.getText().toString().trim().equals("")) {
                    registerConfirmPasswordEdtxt.setError(getString(R.string.loginActivity_passwordConfirmationWarning));
                    isValid = false;
                }
                if(registerNameEdtxt.getText().toString().trim().equals("")) {
                    registerNameEdtxt.setError(getString(R.string.loginActivity_nameWarning));
                    isValid = false;
                }
                if(!registerConfirmPasswordEdtxt.getText().toString().trim().equals(registerPasswordEdtxt.getText().toString().trim())) {
                    registerConfirmPasswordEdtxt.setError(getString(R.string.loginActivity_passwordMatchWarning));
                    isValid = false;
                }

                if(isValid) {
                    doRegister();
                }
            }
        });
    }

    private void loginFunction() {
        doLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if(loginEmailEdtxt.getText().toString().trim().equals("")) {
                    loginEmailEdtxt.setError(getString(R.string.loginActivity_emailWarning));
                    isValid = false;
                }
                if(loginPasswordEdtxt.getText().toString().trim().equals("")) {
                    loginPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                    isValid = false;
                }

                if(isValid) {
                    doLogin();
                }
            }
        });
    }

    private void registerCompleteFunction() {
        doRegisterCompleteLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginView();
            }
        });
    }

    private void forgotPasswordFunction() {
        doForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if(forgotPasswordEmailEdtxt.getText().toString().trim().equals("")) {
                    forgotPasswordEmailEdtxt.setError(getString(R.string.loginActivity_emailWarning));
                    isValid = false;
                }

                if(isValid) {
                    doForgotPassword();
                }
            }
        });
    }

    private void forgotPasswordCompleteFunction() {
        doForgotPasswordCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginView();
            }
        });
    }

    private void toLandingView() {
        loginLandingContainer.setVisibility(View.VISIBLE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);
        forgotPasswordContainer.setVisibility(View.GONE);
        forgotPasswordCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_LANDING;
    }

    private void toRegisterView() {
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.VISIBLE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);
        forgotPasswordContainer.setVisibility(View.GONE);
        forgotPasswordCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_REGISTER;
    }

    private void toLoginView() {
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.VISIBLE);
        registerCompleteContainer.setVisibility(View.GONE);
        forgotPasswordContainer.setVisibility(View.GONE);
        forgotPasswordCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_LOGIN;
    }

    private void toRegisterCompleteView() {
        registerEmailEdtxt.setText("");
        registerPasswordEdtxt.setText("");
        registerConfirmPasswordEdtxt.setText("");
        registerNameEdtxt.setText("");

        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.VISIBLE);
        forgotPasswordContainer.setVisibility(View.GONE);
        forgotPasswordCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_REGISTER_COMPLETE;
    }

    private void toForgotPasswordView() {
        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);
        forgotPasswordContainer.setVisibility(View.VISIBLE);
        forgotPasswordCompleteContainer.setVisibility(View.GONE);

        MENU_FLAG = MENU_FLAG_FORGOT_PASSWORD;
    }

    private void toForgotPasswordCompleteView() {
        forgotPasswordEmailEdtxt.setText("");

        loginLandingContainer.setVisibility(View.GONE);
        registerContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        registerCompleteContainer.setVisibility(View.GONE);
        forgotPasswordContainer.setVisibility(View.GONE);
        forgotPasswordCompleteContainer.setVisibility(View.VISIBLE);

        MENU_FLAG = MENU_FLAG_FORGOT_PASSWORD_COMPLETE;
    }

    private void doLogin() {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", loginEmailEdtxt.getText().toString());
        params.put("password", loginPasswordEdtxt.getText().toString());

        myCillinAPI.doLogin(params).enqueue(new Callback<ModelResultLogin>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultLogin> call, @NonNull Response<ModelResultLogin> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultLogin modelResultLogin = response.body();
                    assert modelResultLogin != null;

                    SessionManager session = new SessionManager(getApplicationContext());
                    session.createLoginSession(
                            modelResultLogin.getResult().getData().getEmail(),
                            modelResultLogin.getResult().getData().getFullName(),
                            modelResultLogin.getResult().getData().getUserId(),
                            modelResultLogin.getResult().getToken(),
                            "",
                            ""
                    );

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    //getUserPic(modelResultLogin);
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
            public void onFailure(@NonNull Call<ModelResultLogin> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doRegister() {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", registerEmailEdtxt.getText().toString());
        params.put("password", registerPasswordEdtxt.getText().toString());
        params.put("name", registerNameEdtxt.getText().toString());
        params.put("ref_id", "");

        myCillinAPI.doRegister(
                    params.get("email"),
                    params.get("password"),
                    params.get("name"),
                    params.get("ref_id"))
        .enqueue(new Callback<ModelResultRegister>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRegister> call, @NonNull Response<ModelResultRegister> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRegister modelResultRegister = response.body();

                    assert modelResultRegister != null;
                    if(modelResultRegister.getResult().isStatus()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRegister.getResult().getMessage(), Snackbar.LENGTH_SHORT).show();

                        toRegisterCompleteView();
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
            public void onFailure(@NonNull Call<ModelResultRegister> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doForgotPassword() {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", forgotPasswordEmailEdtxt.getText().toString());
        
        myCillinAPI.doForgotPassword(params).enqueue(new Callback<ModelResultForgotPassword>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultForgotPassword> call, @NonNull Response<ModelResultForgotPassword> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultForgotPassword modelResultForgotPassword = response.body();

                    assert modelResultForgotPassword != null;
                    if(modelResultForgotPassword.getResult().isStatus()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultForgotPassword.getResult().getMessage(), Snackbar.LENGTH_SHORT).show();

                        toForgotPasswordCompleteView();
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
            public void onFailure(@NonNull Call<ModelResultForgotPassword> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    protected void getFacebookUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response) {
                        try {
                            String picture = json_object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String id = json_object.getString("id");
                            String email = json_object.has("email") ? json_object.getString("email") : "N/A";
                            String name = json_object.getString("name");

                            doFacebookLogin(picture, id, email, name);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    private void doFacebookLogin(final String picture, String id, String email, String name) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("fb_id", id);
        params.put("fb_name", name);
        params.put("fb_email", email.equals("N/A") ? id + "@facebook.com" : email);

        myCillinAPI.doFacebookLogin(
                params.get("fb_id"),
                params.get("fb_name"),
                params.get("fb_email"))
                .enqueue(new Callback<ModelResultFacebookLogin>() {
                    @Override
                    public void onResponse(@NonNull Call<ModelResultFacebookLogin> call, @NonNull Response<ModelResultFacebookLogin> response) {
                        progressBarHandler.hide();

                        if(response.isSuccessful()) {
                            ModelResultFacebookLogin modelResultFacebookLogin = response.body();

                            assert modelResultFacebookLogin != null;
                            if(modelResultFacebookLogin.getResult().isStatus()) {
                                SessionManager session = new SessionManager(getApplicationContext());
                                session.createLoginSession(
                                        modelResultFacebookLogin.getResult().getData().getEmail(),
                                        modelResultFacebookLogin.getResult().getData().getFullName(),
                                        modelResultFacebookLogin.getResult().getData().getUserId(),
                                        modelResultFacebookLogin.getResult().getToken(),
                                        picture, ""
                                );

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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
                    public void onFailure(@NonNull Call<ModelResultFacebookLogin> call, @NonNull Throwable t) {
                        // TODO: 12/10/2017 SET FAILURE SCENARIO
                        progressBarHandler.hide();
                        Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
