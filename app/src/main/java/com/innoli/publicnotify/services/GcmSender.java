package com.innoli.publicnotify.services;

import android.location.Location;
import android.provider.Settings;
import android.util.Log;

import com.google.common.base.Strings;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by yli on 4/10/2016.
 */
public class GcmSender {

  private static final String API_KEY = "AIzaSyAZks_JrMqD0xJV6NkxObM48xTnl6xMM4E";

  private static final String TAG = "gcmsender";

  private static String deviceId;

  /**
   * Set the device id.
   * @param deviceId device id.
   */
  public static void setDeviceId(String deviceId) {
    GcmSender.deviceId = deviceId;
  }

  /**
   * Send notification.
   */
  public static void send(String sender, String senderGroup, String message) {

    try {
      JSONObject jGcmData = new JSONObject();
      JSONObject jData = new JSONObject();

      Location currentLocation = LocationProvider.getInstance().getCurrentLocation();
      if (currentLocation != null) {
        jData.put("latitude", currentLocation.getLatitude());
        jData.put("longitude", currentLocation.getLongitude());
        Log.d(TAG, "current location is " + currentLocation.toString());
      } else {
        Log.d(TAG, "can't get the current location.");
      }

      Calendar c = Calendar.getInstance();
      long seconds = c.getTime().getTime();
      jData.put("now", seconds);
      Log.d(TAG, "now is " + seconds);

      if (!Strings.isNullOrEmpty(deviceId)) {
        jData.put("device_id", deviceId);
      }
      Log.d(TAG, "device id " + deviceId);

      jData.put("sender", sender);
      jData.put("group", senderGroup);
      jData.put("message", message);

      jGcmData.put("to", "/topics/global");
      jGcmData.put("data", jData);

      URL url = new URL("https://android.googleapis.com/gcm/send");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestProperty("Authorization", "key=" + API_KEY);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);

      // Send GCM message content.
      OutputStream outputStream = conn.getOutputStream();
      outputStream.write(jGcmData.toString().getBytes());

      // Read GCM response.
      InputStream inputStream = conn.getInputStream();
      String resp = IOUtils.toString(inputStream);

      Log.i(TAG, "Received the response from GCM server");
      Log.i(TAG, resp);

    } catch (JSONException e) {
      Log.d(TAG, e.getMessage());
    } catch (MalformedURLException e) {
      Log.d("dinnerComing_gcmSender", e.getMessage());
    } catch (ProtocolException e) {
      Log.d("dinnerComing_gcmSender", e.getMessage());
    } catch (IOException e) {
      Log.d("dinnerComing_gcmSender", e.getMessage());
    }


  }

  public static String getDeviceId() {
    return deviceId;
  }
}
