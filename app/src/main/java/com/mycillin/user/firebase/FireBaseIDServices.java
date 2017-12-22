package com.mycillin.user.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireBaseIDServices extends FirebaseInstanceIdService {
    private static final String TAG = "firebase";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("#8#8#", "onTokenRefresh: "+refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        FirebaseManager firebaseManager = new FirebaseManager(getApplicationContext());
        firebaseManager.setFirebaseToken(token);
    }
}
