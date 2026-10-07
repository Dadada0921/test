package com.example.xunyouhook;

import android.app.Application;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Method;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "XunyouLsposed";
    private static final String TARGET_PACKAGE = "com.xunyou.rb";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {

        if (!TARGET_PACKAGE.equals(lpparam.packageName)) {
            return;
        }

        XposedBridge.log(TAG + ": ===== APP LOADED =====");

        try {
            Class<?> appClass = XposedHelpers.findClass(
                    "com.xunyou.rb.MyApplication",
                    lpparam.classLoader
            );

            XposedBridge.log(TAG + ": MyApplication found");

            XposedHelpers.findAndHookMethod(
                    Application.class,
                    "onCreate",
                    new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            if (param.thisObject != null &&
                                    param.thisObject.getClass().getName()
                                            .equals("com.xunyou.rb.MyApplication")) {

                                XposedBridge.log(
                                        TAG + ": MyApplication.onCreate BEFORE"
                                );
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            if (param.thisObject != null &&
                                    param.thisObject.getClass().getName()
                                            .equals("com.xunyou.rb.MyApplication")) {

                                XposedBridge.log(
                                        TAG + ": MyApplication.onCreate AFTER"
                                );
                            }
                        }
                    }
            );

            XposedBridge.log(TAG + ": Application.onCreate hook installed");

        } catch (Throwable e) {
            XposedBridge.log(TAG + ": ERROR: " + e);
        }
    }
}
