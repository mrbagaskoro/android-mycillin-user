package com.mycillin.user.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.mycillin.user.R;
import com.mycillin.user.fragment.PrivacyPolicyFragment;
import com.mycillin.user.fragment.TermsAndConditionsFragment;
import com.mycillin.user.fragment.TermsOfUseFragment;
import com.mycillin.user.util.Configs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndPrivacyPolicyActivity extends AppCompatActivity {

    @BindView(R.id.termAndPrivacyPolicyActivity_tabs)
    TabLayout tabLayout;
    @BindView(R.id.termAndPrivacyPolicyActivity_viewPager)
    ViewPager viewPager;

    @BindView(R.id.termAndPrivacyPolicyActivity_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy_policy);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.accountActivity_termsPrivacyPolicyTitle);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TermsOfUseFragment(), getString(R.string.termAndPrivacyPolicyActivity_termOfUse));
        adapter.addFragment(new TermsAndConditionsFragment(), getString(R.string.termAndPrivacyPolicyActivity_termsAndConditions));
        adapter.addFragment(new PrivacyPolicyFragment(), getString(R.string.termAndPrivacyPolicyActivity_privacyPolicy));
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
