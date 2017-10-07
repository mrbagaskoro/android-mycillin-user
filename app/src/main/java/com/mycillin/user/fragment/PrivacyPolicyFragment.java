package com.mycillin.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.mycillin.user.R;
import com.mycillin.user.util.Configs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicyFragment extends Fragment {

    @BindView(R.id.privacyPolicyFragment_wv_webView)
    WebView webView;

    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        ButterKnife.bind(this, rootView);

        webView.loadUrl(Configs.URL_PRIVACY_POLICY);

        return rootView;
    }
}
