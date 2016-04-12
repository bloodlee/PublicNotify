package com.innoli.publicnotify.services;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class PnInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Log.i("PnInstanceIDLListener", "token is refreshed");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}