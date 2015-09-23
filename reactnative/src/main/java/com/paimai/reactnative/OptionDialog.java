package com.paimai.reactnative;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.react.devsupport.DevSupportManager;

/**
 * Created by moxun on 15/9/19.
 */
public class OptionDialog extends DialogFragment {

    private View rootView;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private RNMainActivity parent;
    private CheckBox checkBox,autoReload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (RNMainActivity) getActivity();
        sharedPreferenceUtil = new SharedPreferenceUtil(getActivity().getApplicationContext());
        rootView = inflater.inflate(R.layout.fragment_option_dialog,container,false);

        checkBox = (CheckBox) rootView.findViewById(R.id.enable_dev_support);
        checkBox.setChecked(sharedPreferenceUtil.isDevSupportEnabled());
        parent.enableDevSupport(checkBox.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store();
                parent.enableDevSupport(checkBox.isChecked());
            }
        });

        autoReload = (CheckBox) rootView.findViewById(R.id.auto_load);
        autoReload.setChecked(sharedPreferenceUtil.isReloadOnJSChange());
        autoReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store();
            }
        });

        getButton(R.id.reload_js).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    store();
                    sharedPreferenceUtil.reloadSettings(parent.getDevSupportManager());
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "please enable dev support before.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getButton(R.id.open_dev_panel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    store();
                    parent.getDevSupportManager().showDevOptionsDialog();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "please enable dev support before.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    private EditText getEditText(int id) {
        return (EditText) rootView.findViewById(id);
    }

    private Button getButton(int id) {
        return (Button) rootView.findViewById(id);
    }

    private void store() {
        sharedPreferenceUtil.setDevSupportEnabled(checkBox.isChecked());
        sharedPreferenceUtil.setReloadOnJSChange(autoReload.isChecked());
    }
}
