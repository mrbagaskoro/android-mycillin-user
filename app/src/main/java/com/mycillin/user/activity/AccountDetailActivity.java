package com.mycillin.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mycillin.user.R;
import com.mycillin.user.adapter.InsuranceAdapter;
import com.mycillin.user.adapter.PaymentAdapter;
import com.mycillin.user.list.InsuranceList;
import com.mycillin.user.list.PaymentList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountDetailActivity extends AppCompatActivity {

    @BindView(R.id.accountDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.accountDetailActivity_ib_addInsurance)
    ImageButton addInsuranceBtn;
    @BindView(R.id.accountDetailActivity_ib_addPayment)
    ImageButton addPaymentBtn;

    @BindView(R.id.accountDetailActivity_rv_insurancesList)
    RecyclerView insuranceRecyclerView;
    @BindView(R.id.accountDetailActivity_rv_paymentsList)
    RecyclerView paymentRecyclerView;

    private List<InsuranceList> insuranceLists = new ArrayList<>();
    private InsuranceAdapter insuranceAdapter;
    private List<PaymentList> paymentLists = new ArrayList<>();
    private PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.accountDetailActivity_title);

        addInsuranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountDetailActivity.this, InsuranceActivity.class);
                startActivity(intent);
            }
        });

        addPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        getInsuranceList();
        getPaymentList();
    }

    public void getInsuranceList() {
        insuranceRecyclerView.setLayoutManager(new LinearLayoutManager(AccountDetailActivity.this));
        insuranceRecyclerView.setItemAnimator(new DefaultItemAnimator());

        insuranceLists.clear();
        insuranceLists.add(new InsuranceList("Prudential", "PR/535722/2016"));
        insuranceLists.add(new InsuranceList("Medicillin", "MC/986620/2017"));

        insuranceAdapter = new InsuranceAdapter(insuranceLists);
        insuranceRecyclerView.setAdapter(insuranceAdapter);
        insuranceAdapter.notifyDataSetChanged();
    }

    public void getPaymentList() {
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(AccountDetailActivity.this));
        paymentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        paymentLists.clear();
        paymentLists.add(new PaymentList("E-Wallet", "1019270000001"));
        paymentLists.add(new PaymentList("PayPal", "PP-871663"));

        paymentAdapter = new PaymentAdapter(paymentLists);
        paymentRecyclerView.setAdapter(paymentAdapter);
        paymentAdapter.notifyDataSetChanged();
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
