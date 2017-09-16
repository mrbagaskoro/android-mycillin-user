package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mycillin.user.R;

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
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
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
