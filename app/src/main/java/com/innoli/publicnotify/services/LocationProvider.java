package com.innoli.publicnotify.services;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * Created by yli on 4/10/2016.
 */
public class LocationProvider {

  private static LocationProvider instance = null;

  private LocationManager locManager;

  private Location currentLoc = null;

  private LocationProvider() {
    // empty constructor.
  }

  /**
   * Get the singleton instance of {@link LocationProvider}
   * @return the instance
   */
  public static synchronized LocationProvider getInstance() {
    if (instance == null) {
      instance = new LocationProvider();
    }
    return instance;
  }

  public void setLocatiotManager(LocationManager locManager) {
    this.locManager = locManager;

    if (this.locManager == null) {
      return;
    }

    LocationListener locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        currentLoc = location;
      }

      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

      public void onProviderEnabled(String provider) {
      }

      public void onProviderDisabled(String provider) {
        currentLoc = null;
      }
    };

    locManager.requestLocationUpdates(
        LocationManager.NETWORK_PROVIDER, (long) 60 * 5 * 1000, 500f, locationListener);
  }

  /**
   * Get current location.
   *
   * @return the location. It will return null if location manger is null.
   */
  public Location getCurrentLocation() {
    return currentLoc;
  }
}
