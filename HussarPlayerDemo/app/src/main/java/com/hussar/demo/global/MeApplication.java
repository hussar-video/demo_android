package com.hussar.demo.global;/**
 * Created by theotian on 16/12/14.
 */

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.hussar.core.HussarManager;

/**
 * Created by theotian on 16/12/14.
 */
public class MeApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        HussarManager.register(this,"demo@hussar.video","cBOeYPVHE1gZteOV8oQvc4zACFBGtD7AH7QYLOucoIWxBXa+1YTYwU9II/Y=");
    }

    private void initObject() {
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        super.unregisterOnProvideAssistDataListener(callback);
    }

    @Override
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        super.registerOnProvideAssistDataListener(callback);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
