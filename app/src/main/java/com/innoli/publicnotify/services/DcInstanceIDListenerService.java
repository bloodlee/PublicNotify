package com.innoli.publicnotify.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class DcInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Log.i("DcInstanceIDLListener", "token is refreshed");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}