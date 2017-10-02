package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.mycillin.user.R;
import com.mycillin.user.util.Configs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndPolicyActivity extends AppCompatActivity {

    @BindView(R.id.termsAndPolicyActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.termsAndPolicyActivity_wv_webView)
    WebView webView;

    public static String EXTRA_FLAG = "EXTRA_FLAG";
    public static String EXTRA_TERMS_OF_USE = "EXTRA_TERMS_OF_USE";
    public static String EXTRA_TERMS_AND_CONDITIONS = "EXTRA_TERMS_AND_CONDITIONS";
    public static String EXTRA_PRIVACY_POLICY = "EXTRA_PRIVACY_POLICY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_policy);
        ButterKnife.bind(this);

        if(getIntent().getStringExtra(EXTRA_FLAG).equals(EXTRA_TERMS_OF_USE)) {
            toolbar.setTitle(R.string.accountActivity_termsOfUseTitle);
            webView.loadUrl(Configs.URL_TERMS_OF_USE);
        }
        else if(getIntent().getStringExtra(EXTRA_FLAG).equals(EXTRA_TERMS_AND_CONDITIONS)) {
            toolbar.setTitle(R.string.accountActivity_termsAndConditionsTitle);
            webView.loadUrl(Configs.URL_TERMS_AND_CONDITIONS);
        }
        else if(getIntent().getStringExtra(EXTRA_FLAG).equals(EXTRA_PRIVACY_POLICY)) {
            toolbar.setTitle(R.string.accountActivity_privacyPolicyTitle);
            webView.loadUrl(Configs.URL_PRIVACY_POLICY);
        }

    }
}
