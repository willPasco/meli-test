package com.will.core.navigation.implementation.di

import com.will.core.navigation.api.controller.NavigationController
import com.will.core.navigation.api.controller.Navigator
import com.will.core.navigation.implementation.controller.NavigationControllerImpl
import com.will.core.navigation.implementation.controller.NavigatorImpl
import org.koin.core.module.Module
import org.koin.dsl.module

public val navigationModule: Module = module {
    single<NavigationController> { NavigationControllerImpl(navigator = get()) }
    single<Navigator> { NavigatorImpl() }
}
