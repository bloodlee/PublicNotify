package com.innoli.publicnotify.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.innoli.publicnotify.MainActivity;
import com.innoli.publicnotify.R;

import java.text.MessageFormat;

public class PnGcmListenerService extends GcmListenerService {

    private static final String TAG = "PnGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String sender = data.getString("sender");
        String group = data.getString("group");

        String deviceId = data.getString("device_id");

        if (Objects.equal(deviceId, GcmSender.getDeviceId())) {
            Log.d(TAG, "device id are the same. The message is from current device.");
        }

        if (Strings.isNullOrEmpty(message)) {
            message = "";
        }

        if (Strings.isNullOrEmpty(sender)) {
            sender = "anonymous";
        }

        if (Strings.isNullOrEmpty(group)) {
            group = "";
        }

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {

        }

        sendNotification(sender, group, message);
    }

    private void sendNotification(String sender, String group, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle("You got a new message!")
                .setContentText(
                    MessageFormat.format("{0} from {1}{2}", message, sender,
                        Strings.isNullOrEmpty(group) ? "" : " in group \"" + group + "\"")
                )
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}