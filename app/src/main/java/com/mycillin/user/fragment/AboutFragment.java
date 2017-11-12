package com.mycillin.user.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mycillin.user.BuildConfig;
import com.mycillin.user.R;
import com.mycillin.user.activity.ChatActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {

    @BindView(R.id.aboutFragment_tv_version)
    TextView version;

    @BindView(R.id.aboutFragment_ll_contact)
    LinearLayout contact;
    @BindView(R.id.aboutFragment_ll_facebook)
    LinearLayout facebook;
    @BindView(R.id.aboutFragment_ll_twitter)
    LinearLayout twitter;
    @BindView(R.id.aboutFragment_ll_instagram)
    LinearLayout instagram;
    @BindView(R.id.aboutFragment_ll_youtube)
    LinearLayout youtube;
    @BindView(R.id.aboutFragment_ll_playStore)
    LinearLayout playstore;

    public static String KEY_EMAIL = "KEY_EMAIL";
    public static String KEY_FACEBOOK = "KEY_FACEBOOK";
    public static String KEY_TWITTER = "KEY_TWITTER";
    public static String KEY_INSTAGRAM = "KEY_INSTAGRAM";
    public static String KEY_YOUTUBE = "KEY_YOUTUBE";
    public static String KEY_PLAYSTORE = "KEY_PLAYSTORE";

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);

        getActivity().setTitle(R.string.nav_about_mycillin);

        version.setText(getResources().getString(R.string.aboutFragment_version) + " " + BuildConfig.VERSION_NAME);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_EMAIL);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_FACEBOOK);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_TWITTER);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_INSTAGRAM);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_YOUTUBE);
            }
        });

        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia(KEY_PLAYSTORE);
            }
        });

        return rootView;
    }

    private void openSocialMedia(String id) {
        String url = "";
        String urlAlt = "";
        String appPackage = "";

        if(id.equals(KEY_EMAIL)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","mycillin@gmail.com", null));
            //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            //intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, getString(R.string.aboutFragment_chooseEmailMessage)));
        }
        else {
            if(id.equals(KEY_FACEBOOK)) {
                url = "fb://profile/100022037055834";
                urlAlt = "http://www.facebook.com/mycillin.mycillin.9/";
                appPackage = "com.facebook.katana";
            }
            else if(id.equals(KEY_TWITTER)) {
                url = "twitter://user?screen_name=mycillin";
                urlAlt = "http://www.twitter.com/mycillin/";
                appPackage = "com.twitter.android";
            }
            else if(id.equals(KEY_INSTAGRAM)) {
                url = "http://instagram.com/_u/mycillin/";
                urlAlt = "https://www.instagram.com/mycillin/";
                appPackage = "com.instagram.android";
            }
            else if(id.equals(KEY_YOUTUBE)) {
                url = "https://www.youtube.com/channel/UCkjlt7RUv_Qi1LPUyTr0o9w/";
                urlAlt = "https://www.youtube.com/channel/UCkjlt7RUv_Qi1LPUyTr0o9w/";
                appPackage = "com.google.android.youtube";
            }
            else if(id.equals(KEY_PLAYSTORE)) {
                url = "market://details?id=" + getContext().getPackageName();
                urlAlt = "https://play.google.com/store/apps/details?id=" + getContext().getPackageName();
                appPackage = "com.android.vending";
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.setPackage(appPackage);
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                new FinestWebView.Builder(getContext()).theme(R.style.WebViewTheme)
                        .titleDefault("MyCillin")
                        .webViewBuiltInZoomControls(true)
                        .webViewDisplayZoomControls(true)
                        .dividerHeight(0)
                        .gradientDivider(false)
                        .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                                R.anim.activity_close_enter, R.anim.activity_close_exit)
                        .show(urlAlt);
            }
        }
    }
}
