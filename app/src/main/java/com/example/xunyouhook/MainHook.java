package com.example.xunyouhook;

import android.app.Application;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "XunyouLsposed";
    private static final String TARGET_PACKAGE = "com.xunyou.rb";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {

        if (!TARGET_PACKAGE.equals(lpparam.packageName)) {
            return;
        }

        XposedBridge.log(TAG + ": APP LOADED");

        // 保留已经验证成功的 Application.onCreate Hook
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
        } catch (Throwable e) {
            XposedBridge.log(TAG + ": Application hook error: " + e);
        }

        // 观察签到页面
        try {
            Class<?> signActivity = XposedHelpers.findClass(
                    "com.xunyou.appuser.ui.activity.EditSignActivity",
                    lpparam.classLoader
            );

            XposedBridge.log(TAG + ": EditSignActivity FOUND");

            XposedHelpers.findAndHookMethod(
                    signActivity,
                    "onCreate",
                    android.os.Bundle.class,
                    new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            XposedBridge.log(
                                    TAG + ": EditSignActivity.onCreate BEFORE"
                            );
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            XposedBridge.log(
                                    TAG + ": EditSignActivity.onCreate AFTER"
                            );
                        }
                    }
            );

            XposedBridge.log(
                    TAG + ": EditSignActivity hook installed"
            );

        } catch (Throwable e) {
            XposedBridge.log(
                    TAG + ": EditSignActivity hook error: " + e
            );
        }
    }
}
