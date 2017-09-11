package com.mycillin.user.util;

import android.util.Log;

import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;

/**
 * Created by 16003041 on 11/09/2017.
 */

public class PermissionErrorListener implements PermissionRequestErrorListener {
    @Override
    public void onError(DexterError error) {
        Log.e("Dexter", "There was an error: " + error.toString());
    }
}
