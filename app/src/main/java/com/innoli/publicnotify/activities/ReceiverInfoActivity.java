package com.innoli.publicnotify.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.common.base.Strings;
import com.innoli.publicnotify.R;
import com.innoli.publicnotify.preferences.PreferenceNames;

public class ReceiverInfoActivity extends AppCompatActivity {

  private SharedPreferences preferences;
  private EditText receiverNameEt;
  private EditText receiverGroupEt;
  private Spinner nearbySpinner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receiver_info);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    preferences = getSharedPreferences(PreferenceNames.COMPOSE_MESSAGE_PREF, MODE_PRIVATE);

    receiverNameEt = (EditText) findViewById(R.id.receiver_name);
    receiverGroupEt = (EditText) findViewById(R.id.receiver_group);

    nearbySpinner = (Spinner) findViewById(R.id.receiver_nearby);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.receiver_info_menu, menu);
    return true;
  }

  public void saveSetting(MenuItem menuItem) {
    String receiverName = Strings.nullToEmpty(receiverNameEt.getText().toString());
    String receiverGroup = Strings.nullToEmpty(receiverGroupEt.getText().toString());

    if (nearbySpinner.getSelectedItemPosition() == 0) {

    }
  }

}
