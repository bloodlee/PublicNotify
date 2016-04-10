package com.innoli.publicnotify;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.common.base.Strings;
import com.innoli.publicnotify.preferences.PreferenceNames;

import java.text.MessageFormat;

/**
 * Activity to compose the message
 */
public class ComposeActivity extends AppCompatActivity {

  private EditText senderNameEditText;
  private EditText msgEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);

    senderNameEditText = (EditText) findViewById(R.id.sender_input);
    msgEditText = (EditText) findViewById(R.id.message_input);

    SharedPreferences prefs =
        getSharedPreferences(PreferenceNames.COMPOSE_MESSAGE_PREF, MODE_PRIVATE);

    String senderName = prefs.getString(PreferenceNames.SENDER_NAME_PREF, "");
    String message = prefs.getString(PreferenceNames.MESSAGE_PREF, "");

    senderNameEditText.setText(senderName);
    msgEditText.setText(message);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.compose_menu, menu);
    return true;
  }

  public void sendMessage(MenuItem menu) {
    String senderName = Strings.nullToEmpty(senderNameEditText.getText().toString());
    String message = Strings.nullToEmpty(msgEditText.getText().toString());

    SharedPreferences.Editor editor =
        getSharedPreferences(PreferenceNames.COMPOSE_MESSAGE_PREF, MODE_PRIVATE).edit();
    editor.putString(PreferenceNames.SENDER_NAME_PREF, senderName);
    editor.putString(PreferenceNames.MESSAGE_PREF, message);
    editor.commit();

    Log.d(ComposeActivity.class.getName(),
        MessageFormat.format("send message {0}: {1}", senderName, message));
  }
}
