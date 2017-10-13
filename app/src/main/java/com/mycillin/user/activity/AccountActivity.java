package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mycillin.user.R;
import com.mycillin.user.adapter.AccountAdapter;
import com.mycillin.user.list.AccountList;
import com.mycillin.user.util.SessionManager;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.accountActivity_ll_manageAccount)
    LinearLayout manageAccount;
    @BindView(R.id.accountActivity_ll_changePassword)
    LinearLayout changePassword;
    @BindView(R.id.accountActivity_ll_signOut)
    LinearLayout signOut;
    @BindView(R.id.accountActivity_ll_termsPrivacyPolicy)
    LinearLayout termsAndPrivacyPolicy;

    @BindView(R.id.accountActivity_toolbar)
    Toolbar toolbar;

    RecyclerView accountRecyclerView;

    private ImageButton addAccountBtn;
    private List<AccountList> accountLists = new ArrayList<>();
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.nav_account);

        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageAccountDialog();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignOut();
            }
        });

        termsAndPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, TermsAndPrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showManageAccountDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(AccountActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_manage_accounts_layout))
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();

        View dialogPlusView = dialogPlus.getHolderView();

        getAccountList(dialogPlusView);

        addAccountBtn = dialogPlusView.findViewById(R.id.manageAccountDialog_ib_addAccountBtn);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showChangePasswordDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(AccountActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_change_password_layout))
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();

        View dialogPlusView = dialogPlus.getHolderView();
    }

    private void doSignOut() {
        new AlertDialog.Builder(AccountActivity.this)
                .setTitle(getString(R.string.accountActivity_signOutTitle))
                .setMessage(R.string.accountActivity_signOutMessage)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(getString(R.string.accountActivity_signOutTitle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void getAccountList(View view) {
        accountRecyclerView = view.findViewById(R.id.manageAccountDialog_rv_recyclertView);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(AccountActivity.this));
        accountRecyclerView.setItemAnimator(new DefaultItemAnimator());

        accountLists.clear();
        accountLists.add(new AccountList("pic_01.jpg", "Me"));
        accountLists.add(new AccountList("pic_01.jpg", "Wife"));
        accountLists.add(new AccountList("pic_01.jpg", "Son"));
        accountLists.add(new AccountList("pic_01.jpg", "Daughter"));

        accountAdapter = new AccountAdapter(accountLists);
        accountRecyclerView.setAdapter(accountAdapter);
        accountAdapter.notifyDataSetChanged();
    }
}
