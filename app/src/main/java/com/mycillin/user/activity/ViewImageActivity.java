package com.mycillin.user.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.mycillin.user.R;
import com.mycillin.user.util.ProgressBarHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageActivity extends AppCompatActivity {

    @BindView(R.id.viewImageActivity_pv_photoView)
    PhotoView photoView;

    private ProgressBarHandler progressBarHandler;

    public static final String EXTRA_IMAGE_BASE_DATA = "EXTRA_IMAGE_BASE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        progressBarHandler = new ProgressBarHandler(ViewImageActivity.this);
        progressBarHandler.show();

        String baseData = getIntent().getStringExtra(EXTRA_IMAGE_BASE_DATA);

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter();

        if(baseData.contains("http://")) {
            Glide.with(getApplicationContext())
                    .load(baseData)
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBarHandler.hide();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarHandler.hide();
                            return false;
                        }
                    })
                    .into(photoView);
        }
        else {
            byte[] imageByteArray = Base64.decode(baseData, Base64.DEFAULT);

            Glide.with(getApplicationContext())
                    .load(imageByteArray)
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBarHandler.hide();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarHandler.hide();
                            return false;
                        }
                    })
                    .into(photoView);
        }
    }
}
