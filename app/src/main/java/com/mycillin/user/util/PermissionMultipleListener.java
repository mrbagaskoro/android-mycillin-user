package com.mycillin.user.util;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mycillin.user.activity.start.PermissionCheckActivity;

import java.util.List;

/**
 * Created by 16003041 on 11/09/2017.
 */

public class PermissionMultipleListener implements MultiplePermissionsListener {

    private final PermissionCheckActivity activity;

    public PermissionMultipleListener(PermissionCheckActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        for (PermissionGrantedResponse response : multiplePermissionsReport.getGrantedPermissionResponses()) {
        }
        if (multiplePermissionsReport.areAllPermissionsGranted()){
            this.activity.checkPermission();
        }

    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {
        activity.showPermissionRationale(token);
    }

}
