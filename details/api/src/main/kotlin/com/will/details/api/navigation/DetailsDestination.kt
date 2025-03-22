package com.will.details.api.navigation

import com.will.core.navigation.api.model.Destination
import com.will.core.navigation.api.model.DestinationConfigs

public class DetailsDestination(
    itemId: String,
    destinationConfigs: DestinationConfigs = DestinationConfigs(),
) : Destination(route = DetailsRoute(itemId), destinationConfigs = destinationConfigs)
