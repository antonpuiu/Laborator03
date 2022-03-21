package lab03.eim.systems.cs.pub.ro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private ImageButton backspace;
    private ImageButton call;
    private ImageButton reject;

    private int buttonIds[] = {
            R.id.number_0_button,
            R.id.number_1_button,
            R.id.number_2_button,
            R.id.number_3_button,
            R.id.number_4_button,
            R.id.number_5_button,
            R.id.number_6_button,
            R.id.number_7_button,
            R.id.number_8_button,
            R.id.number_9_button,
            R.id.star_button,
            R.id.pound_button
    };

    private static int PERMISSION_REQUEST_CALL_PHONE = 1;

    private class GenericButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((TextView) findViewById(R.id.phone_number_edit_text)).append(((Button) view).getText());
        }
    }

    private class CallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private class RejectButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class BackspaceButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (phoneNumber.getText().length() > 0)
                phoneNumber.setText(phoneNumber.getText().subSequence(0, phoneNumber.getText().length() - 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = (EditText) findViewById(R.id.phone_number_edit_text);
        backspace = (ImageButton) findViewById(R.id.backspace_image_button);
        call = (ImageButton) findViewById(R.id.call_image_button);
        reject = (ImageButton) findViewById(R.id.hangup_image_button);

        backspace.setOnClickListener(new BackspaceButtonListener());
        call.setOnClickListener(new CallButtonListener());
        reject.setOnClickListener(new RejectButtonListener());

        for (int i : buttonIds)
            findViewById(i).setOnClickListener(new GenericButtonListener());
    }
}