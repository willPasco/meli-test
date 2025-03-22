package com.will.core.navigation.api.model

public open class Destination(
    public val route: Any,
    public val destinationConfigs: DestinationConfigs = DestinationConfigs()
) : NavigationIntent
