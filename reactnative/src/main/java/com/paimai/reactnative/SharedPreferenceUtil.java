package com.paimai.reactnative;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.react.devsupport.DevInternalSettings;
import com.facebook.react.devsupport.DevSupportManager;

/**
 * Created by moxun on 15/9/19.
 */
public class SharedPreferenceUtil {
    private  DevSupportManager mManager;
    private  Context mContext;
    private  DevInternalSettings settings;
    private  SharedPreferences preferences;
    private  final String PREFS_FPS_DEBUG_KEY = "fps_debug";
    private  final String PREFS_DEBUG_SERVER_HOST_KEY = "debug_http_host";
    private  final String PREFS_ANIMATIONS_DEBUG_KEY = "animations_debug";
    private  final String PREFS_RELOAD_ON_JS_CHANGE_KEY = "reload_on_js_change";
    private  final String PREFS_BUNDLE_NAME = "bundle_name";
    private  final String PREFS_MODULE_NAME = "module_name";
    private  final String PREFS_JS_NAME = "js_name";
    private  final String PREFS_ENABLE_DEV_SUPPORT = "dev_support";

    public SharedPreferenceUtil(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
    }

    public void reloadSettings(DevSupportManager supportManager) {
        supportManager.setDevSupportEnabled(true);
        supportManager.handleReloadJS();
    }

    public void setReloadOnJSChange(boolean can) {
        preferences.edit().putBoolean(PREFS_RELOAD_ON_JS_CHANGE_KEY,can).commit();
    }

    public boolean isReloadOnJSChange() {
        return preferences.getBoolean(PREFS_RELOAD_ON_JS_CHANGE_KEY,false);
    }

    public void setHost(String host) {
        preferences.edit().putString(PREFS_DEBUG_SERVER_HOST_KEY,host).commit();
    }

    public void setBundleName(String name) {
        preferences.edit().putString(PREFS_BUNDLE_NAME,name).commit();
    }

    public void setModuleName(String name) {
        preferences.edit().putString(PREFS_MODULE_NAME,name).commit();
    }

    public void setJSName(String name) {
        preferences.edit().putString(PREFS_JS_NAME,name).commit();
    }

    public void setDevSupportEnabled(boolean enabled) {
        preferences.edit().putBoolean(PREFS_ENABLE_DEV_SUPPORT, enabled).commit();
    }

    public String getHost() {
        return preferences.getString(PREFS_DEBUG_SERVER_HOST_KEY,"127.0.0.1");
    }

    public String getBundleName() {
        return preferences.getString(PREFS_BUNDLE_NAME,"bundle/index.android.bundle");
    }

    public String getModuleName() {
        return preferences.getString(PREFS_MODULE_NAME,"HelloWorld");
    }

    public String getJSName() {
        return preferences.getString(PREFS_JS_NAME,"index.android");
    }

    public boolean isDevSupportEnabled() {
        return preferences.getBoolean(PREFS_ENABLE_DEV_SUPPORT,false);
    }
}
