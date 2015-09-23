package com.paimai.reactnative;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RNControllerActivity extends Activity {

    SharedPreferenceUtil sharedPreferenceUtil;
    EditText jsName;
    EditText moduleName;
    EditText bundleName;
    EditText hostName;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rncontroller);
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        jsName = getEditText(R.id.js_name);
        moduleName = getEditText(R.id.module_name);
        bundleName = getEditText(R.id.bundle_name);
        hostName = getEditText(R.id.server_host);

        jsName.setText(sharedPreferenceUtil.getJSName());
        moduleName.setText(sharedPreferenceUtil.getModuleName());
        bundleName.setText(sharedPreferenceUtil.getBundleName());
        hostName.setText(sharedPreferenceUtil.getHost());

        getButton(R.id.launch_rn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store();
                startActivity(new Intent(RNControllerActivity.this, RNMainActivity.class));
            }
        });

        checkBox = (CheckBox) findViewById(R.id.enable_dev_support);
        checkBox.setChecked(sharedPreferenceUtil.isDevSupportEnabled());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store();
            }
        });
    }

    private EditText getEditText(int id) {
        return (EditText) findViewById(id);
    }

    private Button getButton(int id) {
        return (Button) findViewById(id);
    }

    private void store() {
        sharedPreferenceUtil.setHost(hostName.getText().toString());
        sharedPreferenceUtil.setBundleName(bundleName.getText().toString());
        sharedPreferenceUtil.setJSName(jsName.getText().toString());
        sharedPreferenceUtil.setModuleName(moduleName.getText().toString());
        sharedPreferenceUtil.setDevSupportEnabled(checkBox.isChecked());
    }
}
