package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.adapter.AccountAdapter;
import com.mycillin.user.list.AccountList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.changePassword.ModelResultChangePassword;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.nav_account);

        progressBarHandler = new ProgressBarHandler(this);

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
        final EditText oldPasswordEdtxt = dialogPlusView.findViewById(R.id.changePasswordDialog_et_oldPassword);
        final EditText newPasswordEdtxt = dialogPlusView.findViewById(R.id.changePasswordDialog_et_newPassword);
        final EditText confirmNewPasswordEdtxt = dialogPlusView.findViewById(R.id.changePasswordDialog_et_confirmNewPassword);
        Button applyChangePasswordBtn = dialogPlusView.findViewById(R.id.changePasswordDialog_bt_applyBtn);

        applyChangePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldPasswordEdtxt.getText().toString().trim().equals("")) {
                    oldPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                }
                else if(newPasswordEdtxt.getText().toString().trim().equals("")) {
                    newPasswordEdtxt.setError(getString(R.string.loginActivity_passwordWarning));
                }
                else if(confirmNewPasswordEdtxt.getText().toString().trim().equals("")) {
                    confirmNewPasswordEdtxt.setError(getString(R.string.loginActivity_passwordConfirmationWarning));
                }
                else {
                    if(confirmNewPasswordEdtxt.getText().toString().trim().equals(newPasswordEdtxt.getText().toString().trim())) {
                        doChangePassword(oldPasswordEdtxt.getText().toString(), newPasswordEdtxt.getText().toString());
                    }
                    else {
                        confirmNewPasswordEdtxt.setError(getString(R.string.loginActivity_passwordMatchWarning));
                    }
                }
            }
        });
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
                                .addCallback(new Snackbar.Callback(){
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
                        String message = jsonObject.getJSONObject("result").getString("message");
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
