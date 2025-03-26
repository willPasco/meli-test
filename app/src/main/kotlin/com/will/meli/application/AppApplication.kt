package com.will.meli.application

import android.app.Application
import com.will.core.LoggerSetup
import com.will.core.navigation.implementation.di.navigationModule
import com.will.core.network.implementation.di.networkModule
import com.will.details.implementation.di.detailsModule
import com.will.listing.implementation.di.listingModule
import com.will.meli.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        LoggerSetup.setup(BuildConfig.DEBUG)
    }

    private fun setupKoin() {
        startKoin {
            koinApplication {
                androidContext(this@AppApplication)
                androidLogger()
                loadKoinModules(
                    listOf(networkModule, navigationModule, listingModule, detailsModule)
                )
            }
        }
    }
}
