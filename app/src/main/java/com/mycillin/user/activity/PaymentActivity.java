package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mycillin.user.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.paymentActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.paymentActivity_ll_ewallet)
    LinearLayout eWallet;

    private CheckBox eWalletDialogIsAgree;
    private ImageButton eWalletDialogSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.paymentActivity_paymentTitle);

        eWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEWalletDialog();
            }
        });
    }

    public void showEWalletDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(PaymentActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_e_wallet_layout))
                .create();
        dialogPlus.show();

        View dialogPlusView = dialogPlus.getHolderView();

        eWalletDialogIsAgree = dialogPlusView.findViewById(R.id.eWalletDialog_cb_iAgreeCheckBox);
        eWalletDialogSave = dialogPlusView.findViewById(R.id.eWalletDialog_ib_saveBtn);

        eWalletDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
    }
}
