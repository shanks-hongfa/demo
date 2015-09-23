package com.paimai.reactnative;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.devsupport.DevInternalSettings;
import com.facebook.react.devsupport.DevSupportManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.paimai.reactnative.BuildConfig;

public class RNMainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName(sharedPreferenceUtil.getBundleName())
                .setJSMainModuleName(sharedPreferenceUtil.getJSName())
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(sharedPreferenceUtil.isDevSupportEnabled())
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, sharedPreferenceUtil.getModuleName(), null);

        setContentView(mReactRootView);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            //JSDownloader.download(this,"https://raw.githubusercontent.com/facebook/react-native/master/Examples/SampleApp/index.android.js");
            mReactInstanceManager.getDevSupportManager().setDevSupportEnabled(true);
            OptionDialog dialog = new OptionDialog();
            dialog.show(getFragmentManager(),null);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mReactInstanceManager.getDevSupportManager().showDevOptionsDialog();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this);
        }
    }

    public void enableDevSupport(boolean enabled) {
        mReactInstanceManager.getDevSupportManager().setDevSupportEnabled(enabled);
    }

    public DevSupportManager getDevSupportManager() {
        return mReactInstanceManager.getDevSupportManager();
    }
}
