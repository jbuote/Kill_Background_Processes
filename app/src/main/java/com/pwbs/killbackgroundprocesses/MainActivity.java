package com.pwbs.killbackgroundprocesses;


import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
private Spinner sp_package_name;
private Button btn_kill_process;
private TextView tv_package_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_package_name = (Spinner)findViewById(R.id.sp_package_name);
        btn_kill_process = (Button)findViewById(R.id.btn_Kill_Process);
        tv_package_name = (TextView)findViewById(R.id.tv_package_name);

        btn_kill_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(tv_package_name.getText().toString());
            }
        });
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_package_name.setText("com.blustream.app");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
