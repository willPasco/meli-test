package com.will.core

import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Singleton object responsible for setting up the appropriate logging tree for the application.
 * It configures Timber to either use a `DebugTree` for debug builds or a `CrashlyticsLogTree`
 * for production builds.
 */
public object LoggerSetup {
    public fun setup(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashlyticsLogTree())
        }
    }
}