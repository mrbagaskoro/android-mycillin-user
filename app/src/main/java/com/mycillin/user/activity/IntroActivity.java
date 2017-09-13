package com.mycillin.user.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.ahmedjazzar.rosetta.LanguageSwitcher;
import com.ahmedjazzar.rosetta.LanguagesListDialogFragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.mycillin.user.R;
import com.mycillin.user.fragment.IntroOneFragment;
import com.mycillin.user.fragment.IntroThreeFragment;
import com.mycillin.user.fragment.IntroTwoFragment;
import com.mycillin.user.util.ApplicationPreferencesManager;
import com.mycillin.user.util.LanguageOptions;

import java.util.HashSet;
import java.util.Locale;

public class IntroActivity extends AppIntro {

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroOneFragment.newInstance(R.layout.fragment_intro_one));
        addSlide(IntroTwoFragment.newInstance(R.layout.fragment_intro_two));
        addSlide(IntroThreeFragment.newInstance(R.layout.fragment_intro_three));
        //addSlide(IntroFourFragment.newInstance(R.layout.fragment_intro_four));

        //setProgressButtonEnabled(false);

        Locale currentLocale = getResources().getConfiguration().locale;
        //Log.d("###", "onCreate: current " + currentLocale.getDisplayName(currentLocale));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Log.d("###", "onCreate: selected " + preferences.getString("user_preferred_language", "en"));

        /*ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        if (!applicationPreferencesManager.isSelectLanguage()) {
            defineLanguage();
            DialogFragment newFragment = ChangeLanguageDialogFragment.newInstance(R.string.app_name, position);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }*/
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
    }

    /*private void defineLanguage() {
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

    public static class ChangeLanguageDialogFragment extends LanguagesListDialogFragment {

        public static ChangeLanguageDialogFragment newInstance(int title, int position) {
            ChangeLanguageDialogFragment frag = new ChangeLanguageDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            args.putInt("position", position);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");
            final int position = getArguments().getInt("position");

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setSingleChoiceItems(LanguageOptions.languageList, position, null);
            builder.setTitle(title);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    AlertDialog alert = (AlertDialog)dialogInterface;
                    int position = alert.getListView().getCheckedItemPosition();

                    onLanguageSelected(position);
                    onPositiveClick();

                    ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getContext());
                    applicationPreferencesManager.selectLanguage();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // CLOSE
                }
            });

            return builder.create();
        }
    }*/
}
