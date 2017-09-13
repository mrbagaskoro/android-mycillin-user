package com.mycillin.user.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.mycillin.user.R;
import com.mycillin.user.fragment.intro.IntroOneFragment;
import com.mycillin.user.fragment.intro.IntroThreeFragment;
import com.mycillin.user.fragment.intro.IntroTwoFragment;
import com.mycillin.user.util.ApplicationPreferencesManager;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroOneFragment.newInstance(R.layout.fragment_intro_one));
        addSlide(IntroTwoFragment.newInstance(R.layout.fragment_intro_two));
        addSlide(IntroThreeFragment.newInstance(R.layout.fragment_intro_three));
        //addSlide(IntroFourFragment.newInstance(R.layout.fragment_intro_four));

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Log.d("###", "onCreate: selected " + preferences.getString("user_preferred_language", "en"));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        applicationPreferencesManager.introDone();

        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        applicationPreferencesManager.introDone();

        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
