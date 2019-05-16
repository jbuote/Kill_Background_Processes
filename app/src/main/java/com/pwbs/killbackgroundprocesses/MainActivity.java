package com.pwbs.killbackgroundprocesses;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private Spinner sp_package_name;
private Button btn_kill_process;
private EditText et_package_name;
private static ArrayAdapter<String> adapter;
private static ArrayList<String> packageNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        sp_package_name = (Spinner)findViewById(R.id.sp_package_name);
        btn_kill_process = (Button)findViewById(R.id.btn_Kill_Process);
        et_package_name = (EditText)findViewById(R.id.et_package_name);

        ((TextView)findViewById(R.id.tv_version)).setText("Version - " + getAppVersionName(getApplicationContext()));

        packageNames = getPackageNames();

        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                packageNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_package_name.setAdapter(adapter);
        sp_package_name.setOnItemSelectedListener(this);





        btn_kill_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pkgName = et_package_name.getText().toString();

                if(!pkgName.isEmpty()) {
                    try {
                        if(!packageNames.contains(et_package_name.getText().toString())) {
                            packageNames.add(et_package_name.getText().toString());
                        }

                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        am.killBackgroundProcesses(et_package_name.getText().toString());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    /*new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Error")
                            .setMessage("Package name not entered.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();*/
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        et_package_name.setText(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public MainActivity() {
        super();
    }

    private ArrayList<String> getPackageNames() {
        ArrayList<String> packageNames = new ArrayList<>();

        packageNames.add("");

        ActivityManager am = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

        for (int i = 0; i < runningAppProcessInfo.size(); i++) {
            packageNames.add(runningAppProcessInfo.get(i).processName.toString());
        }

        return packageNames;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*adapter.clear();
        adapter = null;

       // packageNames = getPackageNames();

        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                packageNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_package_name.setAdapter(adapter);
        sp_package_name.invalidate();*/

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


    private static String getAppVersionName(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
