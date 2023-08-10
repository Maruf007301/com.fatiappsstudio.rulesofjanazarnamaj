package com.fatiappsstudio.rulesofjanazarnamaj;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by Nishat on 2/14/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = SampleAppActivity.newIntent(getApplicationContext());
        startActivity(intent);
        finish();
    }
}
