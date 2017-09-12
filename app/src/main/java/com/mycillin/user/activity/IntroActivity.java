package com.mycillin.user.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.ahmedjazzar.rosetta.LanguageSwitcher;
import com.github.paolorotolo.appintro.AppIntro;
import com.mycillin.user.R;
import com.mycillin.user.fragment.IntroFourFragment;
import com.mycillin.user.fragment.IntroOneFragment;
import com.mycillin.user.fragment.IntroThreeFragment;
import com.mycillin.user.fragment.IntroTwoFragment;

import java.util.HashSet;
import java.util.Locale;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroOneFragment.newInstance(R.layout.fragment_intro_one));
        addSlide(IntroTwoFragment.newInstance(R.layout.fragment_intro_two));
        addSlide(IntroThreeFragment.newInstance(R.layout.fragment_intro_three));
        //addSlide(IntroFourFragment.newInstance(R.layout.fragment_intro_four));

        //setProgressButtonEnabled(false);

        Locale currentLocale = getResources().getConfiguration().locale;
        Log.d("###", "onCreate: " + currentLocale.getDisplayName(currentLocale));

        defineLanguage();
        new LanguageSwitcher(getApplicationContext()).showChangeLanguageDialog(IntroActivity.this);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
    }

    private void defineLanguage() {
        // This is the locale that you wanna your app to launch with.
        Locale firstLaunchLocale = new Locale("en");

        // You can use a HashSet<String> instead and call 'setSupportedStringLocales()' :)
        HashSet<Locale> supportedLocales = new HashSet<>();
        supportedLocales.add(new Locale("in"));
        supportedLocales.add(firstLaunchLocale);

        // You can make the following object static so you can use the same reference in all app's
        // classes. static is much stable.
        LanguageSwitcher ls = new LanguageSwitcher(getApplicationContext(), firstLaunchLocale);
        ls.setSupportedLocales(supportedLocales);
    }
}
