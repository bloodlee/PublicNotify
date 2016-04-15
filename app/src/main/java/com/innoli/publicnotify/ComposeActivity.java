package com.innoli.publicnotify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.innoli.publicnotify.activities.ReceiverInfoActivity;
import com.innoli.publicnotify.preferences.PreferenceNames;
import com.innoli.publicnotify.services.GcmSender;

import org.w3c.dom.Text;

import java.text.MessageFormat;

/**
 * Activity to compose the message
 */
public class ComposeActivity extends AppCompatActivity {

  private EditText senderNameEditText;
  private EditText msgEditText;
  private TextView msgLabel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);

//    senderNameEditText = (EditText) findViewById(R.id.sender_input);
//    msgEditText = (EditText) findViewById(R.id.message_input);
//
//    SharedPreferences prefs =
//        getSharedPreferences(PreferenceNames.COMPOSE_MESSAGE_PREF, MODE_PRIVATE);
//
//    String senderName = prefs.getString(PreferenceNames.SENDER_NAME_PREF, "");
//    String message = prefs.getString(PreferenceNames.MESSAGE_PREF, "");
//
//    senderNameEditText.setText(senderName);
//    msgEditText.setText(message);

    ImageButton editToButton = (ImageButton) findViewById(R.id.editTo);
    editToButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ComposeActivity.this, ReceiverInfoActivity.class);
        startActivity(intent);
      }
    });

    msgLabel = (TextView) findViewById(R.id.messageLabel);

    msgEditText = (EditText) findViewById(R.id.messageText);
    msgEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        updateMsgLabel();
      }
    });

    updateMsgLabel();
  }
  private void updateMsgLabel() {
    msgLabel.setText(
        MessageFormat.format("Message ({0}/150):",
            msgEditText.getText().toString().length())
    );

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

    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8)
    {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
          .permitAll().build();
      StrictMode.setThreadPolicy(policy);
    }

    GcmSender.send(senderName, message);

    Toast toast =
        Toast.makeText(getApplicationContext(), "Message has been sent!", Toast.LENGTH_SHORT);
    toast.show();

    // close and back to previous activity.
    finish();
  }
}
