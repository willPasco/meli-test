package com.will.core

import timber.log.Timber
import timber.log.Timber.DebugTree

public object LoggerSetup {
    public fun setup(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashlyticsLogTree())
        }
    }
}