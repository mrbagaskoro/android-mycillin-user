package com.mycillin.user.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by mrbagaskoro on 15-Oct-17.
 */

public class ProgressBarHandler {
    private ProgressBar progressBar;
    private Context context;

    public ProgressBarHandler(Context context) {

        this.context = context;

        ViewGroup layout = (ViewGroup) ((Activity) this.context).findViewById(android.R.id.content).getRootView();

        progressBar = new ProgressBar(this.context, null, android.R.attr.progressBarStyle);
        progressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(this.context);

        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);

        layout.addView(rl, params);

        ((Activity) this.context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        hide();
    }

    public void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        if(progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            ((Activity) this.context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
