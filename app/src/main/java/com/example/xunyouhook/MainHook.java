package com.example.xunyouhook;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "XunyouLsposed";
    private static final String TARGET_PACKAGE = "com.xunyou.rb";
    private static final String SIGN_ACTIVITY =
            "com.xunyou.appuser.ui.activity.EditSignActivity";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {

        if (!TARGET_PACKAGE.equals(lpparam.packageName)) {
            return;
        }

        XposedBridge.log(TAG + ": APP LOADED");

        /*
         * 1. 保留已经验证成功的 Application Hook
         */
        try {
            XposedHelpers.findAndHookMethod(
                    Application.class,
                    "onCreate",
                    new XC_MethodHook() {

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

            XposedBridge.log(TAG + ": Application hook installed");

        } catch (Throwable e) {
            XposedBridge.log(
                    TAG + ": Application hook error: " + e
            );
        }


        /*
         * 2. Hook Activity.onCreate()
         *
         * 不直接 Hook EditSignActivity.onCreate()
         * 因为它没有声明这个方法。
         */
        try {

            XposedHelpers.findAndHookMethod(
                    Activity.class,
                    "onCreate",
                    Bundle.class,
                    new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(
                                MethodHookParam param) {

                            if (param.thisObject != null &&
                                    SIGN_ACTIVITY.equals(
                                            param.thisObject
                                                    .getClass()
                                                    .getName())) {

                                XposedBridge.log(
                                        TAG + ": ===== SIGN ACTIVITY OPEN ====="
                                );
                            }
                        }

                        @Override
                        protected void afterHookedMethod(
                                MethodHookParam param) {

                            if (param.thisObject != null &&
                                    SIGN_ACTIVITY.equals(
                                            param.thisObject
                                                    .getClass()
                                                    .getName())) {

                                XposedBridge.log(
                                        TAG + ": ===== SIGN ACTIVITY READY ====="
                                );
                            }
                        }
                    }
            );

            XposedBridge.log(
                    TAG + ": Activity.onCreate hook installed"
            );

        } catch (Throwable e) {

            XposedBridge.log(
                    TAG + ": Activity hook error: " + e
            );
        }
    }
}
