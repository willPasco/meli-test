package com.will.core

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import timber.log.Timber

/**
 * Custom Timber tree that logs exceptions to Firebase Crashlytics.
 *
 * This tree should be used only in production flavors, it will prevent to waste resources in
 * debug environment, also it will not log only ERROR and WARN log levels.
 */
public class CrashlyticsLogTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        if (t != null) {
            if (priority == Log.ERROR || priority == Log.WARN) {
                Firebase.crashlytics.recordException(t)
            }
        }
    }
}
