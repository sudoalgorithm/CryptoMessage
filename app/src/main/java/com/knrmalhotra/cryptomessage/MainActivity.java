package com.knrmalhotra.cryptomessage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

  private EditText mEditText;
  private TextView mTextView1, mTextView2;
  private Button mButton1, mButton2;
  private Toolbar mToolbar;
  private String mMessage;
  private ActionBar mActionBar;
  private byte[] encryptedMessage;
  private KeyPairGenerator mKeyPairGenerator;
  private KeyPair mKeyPair;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
    setSupportActionBar(mToolbar);

    mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      mActionBar.setDisplayShowHomeEnabled(true);
      mActionBar.setDisplayHomeAsUpEnabled(true);
      mActionBar.setDisplayShowTitleEnabled(false);
    }

    mEditText = (EditText) findViewById(R.id.et_1);
    mTextView1 = (TextView) findViewById(R.id.display_text_view);
    mTextView2 = (TextView) findViewById(R.id.display_text_view_decrypted);
    mButton1 = (Button) findViewById(R.id.btn_1);
    mButton2 = (Button) findViewById(R.id.btn_2);

    mMessage = mEditText.getText().toString();

    mButton1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String textToBeDisplayed = null;
        try {
          textToBeDisplayed = encryptedText();
        } catch (Exception e) {
          e.printStackTrace();
        }

        mTextView1.setText(textToBeDisplayed);
      }
    });

    mButton2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String textToBeDisplayedDecrypted = null;
        try {
          textToBeDisplayedDecrypted = decryptedText();
        } catch (Exception e) {
          e.printStackTrace();
        }
        mTextView2.setText(textToBeDisplayedDecrypted);
        Log.d("Button Pressed", String.valueOf(mTextView2));
      }
    });
  }

  public String encryptedText() throws Exception {
    mKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
    mKeyPairGenerator.initialize(1024);

    mKeyPair = mKeyPairGenerator.generateKeyPair();
    PublicKey mPublicKey = mKeyPair.getPublic();

    String messageToBeEncrypted = mMessage;

    byte[] bytesToBeEncrypted = messageToBeEncrypted.getBytes("UTF-8");
    Cipher mCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    mCipher.init(Cipher.ENCRYPT_MODE, mPublicKey);
    encryptedMessage = mCipher.doFinal(bytesToBeEncrypted);
    String encryptedText = encryptedMessage.toString();

    return "RSA Encryption:- " + encryptedText;
  }

  public String decryptedText() throws Exception {
    PrivateKey mPrivateKey = mKeyPair.getPrivate();
    Cipher cip = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cip.init(Cipher.DECRYPT_MODE, mPrivateKey);
    byte[] decrypted = cip.doFinal(encryptedMessage);
    String decryptedMessage = decrypted.toString();

    return "Decrypted Text:- " + decryptedMessage;
  }
}
