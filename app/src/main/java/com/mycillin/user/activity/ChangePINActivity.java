package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mycillin.user.R;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

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

            if(isValid) {
                // TODO: 01-Dec-17 DO SAVE PIN
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.setPINAvailable();
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
