package com.mycillin.user.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmedjazzar.rosetta.LanguageSwitcher;
import com.ahmedjazzar.rosetta.LanguagesListDialogFragment;
import com.mycillin.user.BuildConfig;
import com.mycillin.user.R;
import com.mycillin.user.database.Banner;
import com.mycillin.user.database.BannerBig;
import com.mycillin.user.database.BannerBigDao;
import com.mycillin.user.database.BannerDao;
import com.mycillin.user.database.DaoSession;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.bannerImage.ModelResultBannerImage;
import com.mycillin.user.rest.bannerImageBig.ModelResultBannerImageBig;
import com.mycillin.user.util.ApplicationPreferencesManager;
import com.mycillin.user.util.BaseApplication;
import com.mycillin.user.util.LanguageOptions;
import com.mycillin.user.util.ProgressBarHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splashScreen_tv_version)
    TextView tvVersion;
    @BindView(R.id.splashScreen_pb_progressBar)
    ProgressBar progressBar;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        defineLanguage();

        tvVersion.setText(getResources().getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

        ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        if (!applicationPreferencesManager.isSelectLanguage()) {
            DialogFragment newFragment = SplashActivity.ChangeLanguageDialogFragment.newInstance(R.string.app_name, position);
            newFragment.setCancelable(false);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
        else {
            getBanner(applicationPreferencesManager);
        }
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

    private void getBanner(final ApplicationPreferencesManager applicationPreferencesManager) {
        progressBar.setVisibility(View.VISIBLE);

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getBannerImage().enqueue(new Callback<ModelResultBannerImage>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultBannerImage> call, @NonNull Response<ModelResultBannerImage> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()) {
                    ModelResultBannerImage modelResultBannerImage = response.body();

                    assert modelResultBannerImage != null;
                    if(modelResultBannerImage.getResult().isStatus()) {
                        List<Banner> bannerList = modelResultBannerImage.getResult().getData();

                        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
                        BannerDao bannerDao = daoSession.getBannerDao();
                        bannerDao.insertOrReplaceInTx(bannerList);

                        getBannerBig(applicationPreferencesManager);
                    }
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if(jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        }
                        else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultBannerImage> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBar.setVisibility(View.GONE);
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getBannerBig(final ApplicationPreferencesManager applicationPreferencesManager) {
        progressBar.setVisibility(View.VISIBLE);

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getBannerImageBig().enqueue(new Callback<ModelResultBannerImageBig>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultBannerImageBig> call, @NonNull Response<ModelResultBannerImageBig> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()) {
                    ModelResultBannerImageBig modelResultBannerImageBig = response.body();

                    assert modelResultBannerImageBig != null;
                    if(modelResultBannerImageBig.getResult().isStatus()) {
                        List<BannerBig> bannerBigList = modelResultBannerImageBig.getResult().getData();

                        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
                        BannerBigDao bannerBigDao = daoSession.getBannerBigDao();
                        bannerBigDao.insertOrReplaceInTx(bannerBigList);

                        if(applicationPreferencesManager.isIntroDone()) {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(SplashActivity.this, PermissionCheckActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if(jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        }
                        else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultBannerImageBig> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBar.setVisibility(View.GONE);
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
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
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    AlertDialog alert = (AlertDialog)dialogInterface;
                    int position = alert.getListView().getCheckedItemPosition();

                    onLanguageSelected(position);
                    onPositiveClick();

                    ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getContext());
                    applicationPreferencesManager.selectLanguage();

                    Intent intent = new Intent(getActivity(), PermissionCheckActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            return builder.create();
        }
    }
}
