package com.mindvalley.app.mindvalley_arun_android_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.misc.ConnectionHelper;

import butterknife.BindView;

/**
 * Created by arun on 4/29/2016.
 */
public class SplashActivity extends AppCompatActivity {

    Intent intent;
    TextView message;
    ImageView app_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        message = (TextView) findViewById(R.id.textView_message);
        app_logo = (ImageView) findViewById(R.id.imageView_logo);

        YoYo.with(Techniques.FadeIn)
                .duration(800)
                .playOn(app_logo);

        moveToMainActivity();
    }

    public void moveToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ConnectionHelper.isConnected(SplashActivity.this)) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.push_down_in, R.transition.push_down_out);
                    finish();
                } else {
                    message.setText(R.string.internet_status_message);
                    YoYo.with(Techniques.FadeIn)
                            .duration(700)
                            .playOn(message);
                }
            }
        }, 2500);
    }
}
