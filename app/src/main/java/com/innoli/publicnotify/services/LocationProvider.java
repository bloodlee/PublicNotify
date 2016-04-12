package com.innoli.publicnotify.services;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by yli on 4/10/2016.
 */
public class LocationProvider {

  private static LocationProvider instance = null;

  private LocationManager locManager;

  private Location currentLoc = null;

  private boolean started = false;

  private LocationListener locationListener = new LocationListener() {
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
  }

  public void startGettingLocation() {
    if (!started) {
      locManager.requestLocationUpdates(
          LocationManager.NETWORK_PROVIDER, (long) 5 * 1000, 200f, locationListener);
      started = true;
    }
  }

  public void stopGettingLocation() {
    if (started) {
      locManager.removeUpdates(locationListener);
      started = false;
    }
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
