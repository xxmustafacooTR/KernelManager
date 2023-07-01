package com.xxmustafacooTR.kernelmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.xxmustafacooTR.kernelmanager.R;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {
    private static PackageInfo sIOInstance;

    public static PackageInfo getInstance() {
        return getInstance(null);
    }

    public static PackageInfo getInstance(Context context) {
        if (sIOInstance == null) {
            sIOInstance = new PackageInfo(context);
        }
        return sIOInstance;
    }

    private ArrayList<PackageInfo> allApps;

    private PackageInfo(Context context) {
        if(context != null)
            savePackages(context);
    }

    public enum Category {
        CATEGORY_NONE,
        CATEGORY_GAME,
        CATEGORY_AUDIO,
        CATEGORY_VIDEO,
        CATEGORY_IMAGE,
        CATEGORY_SOCIAL,
        CATEGORY_NEWS,
        CATEGORY_MAPS,
        CATEGORY_PRODUCTIVITY,
        CATEGORY_ACCESSIBILITY,
        CATEGORY_SYSTEM,
    }

    private String appName;
    private String packageName;
    private String versionName;
    private int versionCode;
    private Category appCategory;

    public void runApp(String packageName, Context context){
        Intent launchApp = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(launchApp);
    }

    public String searchAppPackageFromName(String appName) {
        for (PackageInfo app: allApps) {
            if(app.appName.contains(appName))
                return app.packageName;
        }
        return null;
    }

    public List<String> getAdjustedAppNames() {
        return getAdjustedAppNames(999);
    }

    public List<String> getAdjustedAppNames(int mode) {
        List<String> packages = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mode == 0)
            mode++;

        for (PackageInfo app: allApps) {
            boolean add = false;

            if (mode >= 999)
                add = true;

            if (!add && mode > 0 && app.appCategory == Category.CATEGORY_GAME)
                add = true;
            if (!add && mode > 1 && app.appCategory == Category.CATEGORY_PRODUCTIVITY)
                add = true;
            if (!add && mode > 2 && app.appCategory == Category.CATEGORY_SOCIAL)
                add = true;
            if (!add && mode > 3 && app.appCategory == Category.CATEGORY_VIDEO)
                add = true;
            if (!add && mode > 4 && app.appCategory == Category.CATEGORY_IMAGE)
                add = true;
            if (!add && mode > 5 && app.appCategory == Category.CATEGORY_AUDIO)
                add = true;
            if (!add && mode > 6 && app.appCategory == Category.CATEGORY_NEWS)
                add = true;
            if (!add && mode > 7 && app.appCategory == Category.CATEGORY_MAPS)
                add = true;
            if (!add && mode > 8 && app.appCategory == Category.CATEGORY_ACCESSIBILITY)
                add = true;
            if (!add && mode > 9 && app.appCategory == Category.CATEGORY_NONE)
                add = true;
            if (!add && mode > 10 && app.appCategory == Category.CATEGORY_SYSTEM)
                add = true;

            if (add)
                packages.add(app.appName);
        }
        return packages;
    }

    public List<String> getAdjustedAppPackages() {
        return getAdjustedAppPackages(999);
    }

    public List<String> getAdjustedAppPackages(int mode) {
        List<String> packages = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mode == 0)
            mode++;

        for (PackageInfo app: allApps) {
            boolean add = false;

            if (mode >= 999)
                add = true;

            if (!add && mode > 0 && app.appCategory == Category.CATEGORY_GAME)
                add = true;
            if (!add && mode > 1 && app.appCategory == Category.CATEGORY_PRODUCTIVITY)
                add = true;
            if (!add && mode > 2 && app.appCategory == Category.CATEGORY_SOCIAL)
                add = true;
            if (!add && mode > 3 && app.appCategory == Category.CATEGORY_VIDEO)
                add = true;
            if (!add && mode > 4 && app.appCategory == Category.CATEGORY_IMAGE)
                add = true;
            if (!add && mode > 5 && app.appCategory == Category.CATEGORY_AUDIO)
                add = true;
            if (!add && mode > 6 && app.appCategory == Category.CATEGORY_NEWS)
                add = true;
            if (!add && mode > 7 && app.appCategory == Category.CATEGORY_MAPS)
                add = true;
            if (!add && mode > 8 && app.appCategory == Category.CATEGORY_ACCESSIBILITY)
                add = true;
            if (!add && mode > 9 && app.appCategory == Category.CATEGORY_NONE)
                add = true;
            if (!add && mode > 10 && app.appCategory == Category.CATEGORY_SYSTEM)
                add = true;

            if (add)
                packages.add(app.packageName);
        }
        return packages;
    }

    public String getAppNameFromPackage(String packageName) {
        for (PackageInfo app: allApps) {
            if(app.packageName.equals(packageName))
                return app.appName;
        }
        return null;
    }

    public String getVersionNameFromPackage(String packageName) {
        for (PackageInfo app: allApps) {
            if(app.packageName.equals(packageName))
                return app.versionName;
        }
        return null;
    }

    public Integer getVersionCodeFromPackage(String packageName) {
        for (PackageInfo app: allApps) {
            if(app.packageName.equals(packageName))
                return app.versionCode;
        }
        return null;
    }

    public Drawable getIconFromPackage(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(packageName,0).applicationInfo.loadIcon(pm);
        } catch (Exception ignored) {

        }
        return null;
    }

    public Category getAppCategoryFromPackage(String packageName) {
        for (PackageInfo app: allApps) {
            if(app.packageName.equals(packageName))
                return app.appCategory;
        }
        return null;
    }

    public PackageInfo getAppFromPackage(String packageName) {
        for (PackageInfo app: allApps) {
            if(app.packageName.equals(packageName))
                return app;
        }
        return null;
    }

    public void savePackages(Context context) {
        ArrayList<PackageInfo> apps = getInstalledApps(context);
        for (PackageInfo app: apps) {
            app.Log();
        }
        allApps = apps;
    }

    private ArrayList<PackageInfo> getInstalledApps(Context context) {
        PackageManager pm = context.getPackageManager();
        ArrayList<PackageInfo> res = new ArrayList<PackageInfo>();
        List<android.content.pm.PackageInfo> packs = pm.getInstalledPackages(0);
        for (android.content.pm.PackageInfo p: packs) {
            PackageInfo newInfo = new PackageInfo(null);
            newInfo.appName = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
            newInfo.packageName = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.appCategory = Category.CATEGORY_NONE;
            if(pm.getLaunchIntentForPackage(p.packageName) != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_PRODUCTIVITY)
                        newInfo.appCategory = Category.CATEGORY_PRODUCTIVITY;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_ACCESSIBILITY)
                        newInfo.appCategory = Category.CATEGORY_ACCESSIBILITY;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_AUDIO)
                        newInfo.appCategory = Category.CATEGORY_AUDIO;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_VIDEO)
                        newInfo.appCategory = Category.CATEGORY_VIDEO;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_IMAGE)
                        newInfo.appCategory = Category.CATEGORY_IMAGE;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_SOCIAL)
                        newInfo.appCategory = Category.CATEGORY_SOCIAL;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_NEWS)
                        newInfo.appCategory = Category.CATEGORY_NEWS;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_MAPS)
                        newInfo.appCategory = Category.CATEGORY_MAPS;
                    if (p.applicationInfo.category == ApplicationInfo.CATEGORY_GAME)
                        newInfo.appCategory = Category.CATEGORY_GAME;
                    if (p.applicationInfo.category == ApplicationInfo.FLAG_SYSTEM ||
                            p.applicationInfo.category == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)
                        newInfo.appCategory = Category.CATEGORY_SYSTEM;
                } else {
                    newInfo.appCategory = Category.CATEGORY_PRODUCTIVITY;

                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_ACCESSIBILITY) == ApplicationInfo.CATEGORY_ACCESSIBILITY)
                        newInfo.appCategory = Category.CATEGORY_ACCESSIBILITY;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_AUDIO) == ApplicationInfo.CATEGORY_AUDIO)
                        newInfo.appCategory = Category.CATEGORY_AUDIO;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_VIDEO) == ApplicationInfo.CATEGORY_VIDEO)
                        newInfo.appCategory = Category.CATEGORY_VIDEO;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_IMAGE) == ApplicationInfo.CATEGORY_IMAGE)
                        newInfo.appCategory = Category.CATEGORY_IMAGE;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_SOCIAL) == ApplicationInfo.CATEGORY_SOCIAL)
                        newInfo.appCategory = Category.CATEGORY_SOCIAL;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_NEWS) == ApplicationInfo.CATEGORY_NEWS)
                        newInfo.appCategory = Category.CATEGORY_NEWS;
                    if ((p.applicationInfo.flags & ApplicationInfo.CATEGORY_MAPS) == ApplicationInfo.CATEGORY_MAPS)
                        newInfo.appCategory = Category.CATEGORY_MAPS;
                }

                if((p.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP ||
                        (p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM)
                    newInfo.appCategory = Category.CATEGORY_SYSTEM;

            }
            res.add(newInfo);
        }
        return res;
    }

    public Boolean hasPackages() {
        return allApps != null;
    }

    public boolean supported() {
        return hasPackages();
    }

    private void Log() {
        Log.i(appName + "\t" + packageName + "\t" + versionName + "\t" + versionCode + "\t" + appCategory);
    }
}