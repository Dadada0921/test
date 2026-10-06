package com.example.xunyouhook;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Minimal LSPosed module for com.xunyou.rb.
 *
 * Current behavior:
 * - Only activates for com.xunyou.rb
 * - Hooks com.xunyou.rb.MyApplication.onCreate()
 * - Does not alter the original return value or arguments
 */
public class MainHook implements IXposedHookLoadPackage {

    private static final String TARGET_PACKAGE = "com.xunyou.rb";
    private static final String TAG = "XunyouLsposed";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {

        if (!TARGET_PACKAGE.equals(lpparam.packageName)) {
            return;
        }

        XposedBridge.log(TAG + ": loaded " + lpparam.packageName);

        try {
            Class<?> applicationClass = XposedHelpers.findClass(
                    "com.xunyou.rb.MyApplication",
                    lpparam.classLoader
            );

            XposedHelpers.findAndHookMethod(
                    applicationClass,
                    "onCreate",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            XposedBridge.log(TAG + ": MyApplication.onCreate() BEFORE");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            XposedBridge.log(TAG + ": MyApplication.onCreate() AFTER");
                        }
                    }
            );

            XposedBridge.log(TAG + ": hook installed");
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": hook failed: " + Log.getStackTraceString(t));
        }
    }
}
