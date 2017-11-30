package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageActivity extends AppCompatActivity {

    @BindView(R.id.viewImageActivity_pv_photoView)
    PhotoView photoView;

    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter();

        Glide.with(getApplicationContext())
                .load(url)
                .apply(requestOptions)
                .into(photoView);
    }
}
