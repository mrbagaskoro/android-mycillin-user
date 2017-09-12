package com.mycillin.user.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.mycillin.user.R;
import com.mycillin.user.fragment.IntroFourFragment;
import com.mycillin.user.fragment.IntroOneFragment;
import com.mycillin.user.fragment.IntroThreeFragment;
import com.mycillin.user.fragment.IntroTwoFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroOneFragment.newInstance(R.layout.fragment_intro_one));
        addSlide(IntroTwoFragment.newInstance(R.layout.fragment_intro_two));
        addSlide(IntroThreeFragment.newInstance(R.layout.fragment_intro_three));
        addSlide(IntroFourFragment.newInstance(R.layout.fragment_intro_four));

        setProgressButtonEnabled(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
    }
}
