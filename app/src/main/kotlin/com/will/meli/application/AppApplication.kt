package com.will.meli.application

import android.app.Application
import com.will.core.navigation.implementation.di.navigationModule
import com.will.core.network.implementation.di.networkModule
import com.will.details.implementation.di.detailsModule
import com.will.listing.implementation.di.listingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@AppApplication)
            androidLogger()
            loadKoinModules(
                listOf(networkModule, navigationModule, listingModule, detailsModule)
            )
        }
    }
}