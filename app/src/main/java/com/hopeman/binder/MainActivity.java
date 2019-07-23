package com.hopeman.binder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hopeman.binder.remote.RemoteWebActivity;
import com.hopeman.constraintlayout.R;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.remote_web_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remote_web_btn:
                startActivity(new Intent(this, RemoteWebActivity.class));
                break;
        }
    }

}
