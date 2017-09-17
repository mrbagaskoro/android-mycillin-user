package com.mycillin.user.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountDetailActivity extends AppCompatActivity {

    @BindView(R.id.accountDetailActivity_ib_addInsurance)
    ImageButton addInsuranceBtn;
    @BindView(R.id.accountDetailActivity_ib_addPayment)
    ImageButton addPaymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.accountDetailActivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.accountDetailActivity_title);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
