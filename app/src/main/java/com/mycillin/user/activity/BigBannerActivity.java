package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mycillin.user.R;
import com.mycillin.user.database.BannerBig;
import com.mycillin.user.util.ApplicationPreferencesManager;
import com.mycillin.user.util.DaoDatabaseHelper;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.thefinestartist.finestwebview.FinestWebView;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigBannerActivity extends AppCompatActivity {

    @BindView(R.id.bigBannerActivity_cv_carouselView)
    CarouselView bigBanner;
    @BindView(R.id.bigBannerActivity_ib_closeButton)
    ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_banner);
        ButterKnife.bind(this);

        getBigBannerImage();

        ApplicationPreferencesManager applicationPreferencesManager = new ApplicationPreferencesManager(getApplicationContext());
        applicationPreferencesManager.notFirstLaunched();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getBigBannerImage() {
        final List<String> imageData = new ArrayList<>();
        final List<String> imageLinks = new ArrayList<>();

        DaoDatabaseHelper daoDatabaseHelper = new DaoDatabaseHelper(BigBannerActivity.this);
        Query<BannerBig> query = daoDatabaseHelper.getBannerBig();

        imageData.clear();
        imageLinks.clear();

        if(query.list().size() > 0) {
            for (int i = 0; i < query.list().size(); i++) {
                String baseData = query.list().get(i).getBaseData();
                String imageLink = query.list().get(i).getUrlLink();
                imageData.add(baseData);
                imageLinks.add(imageLink);
            }

            bigBanner.setPageCount(query.list().size());
            bigBanner.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    RequestOptions requestOptions = new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.banner_default)
                            .fitCenter();

                    byte[] imageByteArray = Base64.decode(imageData.get(position), Base64.DEFAULT);

                    Glide.with(getApplicationContext())
                            .load(imageByteArray)
                            .apply(requestOptions)
                            .into(imageView);
                }
            });

            bigBanner.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                    new FinestWebView.Builder(getApplicationContext()).theme(R.style.WebViewTheme)
                            .titleDefault("MyCillin")
                            .webViewBuiltInZoomControls(true)
                            .webViewDisplayZoomControls(true)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                                    R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show(imageLinks.get(position));
                }
            });
        }
        else {
            finish();
        }
    }
}
