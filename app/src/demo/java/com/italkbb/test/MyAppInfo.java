package com.italkbb.test;

import android.graphics.drawable.Drawable;

/**
 * Created by WangSiYe on 2019/1/21.
 */
public class MyAppInfo {
    private Drawable image;
    private String appName;
    private String packageName;

    public MyAppInfo() {

    }

    public MyAppInfo(Drawable image, String appName, String packageName) {
        this.image = image;
        this.appName = appName;
        this.packageName = packageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
