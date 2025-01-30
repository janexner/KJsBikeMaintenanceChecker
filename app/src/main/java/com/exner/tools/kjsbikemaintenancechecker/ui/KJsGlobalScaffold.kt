package com.exner.tools.kjsbikemaintenancechecker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.AboutDestination
import com.ramcosta.composedestinations.generated.destinations.BikeAddDestination
import com.ramcosta.composedestinations.generated.destinations.ComponentAddDestination
import com.ramcosta.composedestinations.generated.destinations.HomeDestination
import com.ramcosta.composedestinations.generated.destinations.ManageBikesAndComponentsDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsDestination
import com.ramcosta.composedestinations.generated.destinations.ShelfDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

@Composable
fun KJsGlobalScaffold(
    windowSizeClass: WindowSizeClass
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()
    val destinationsNavigator = navController.rememberDestinationsNavigator()
    val destination = navController.currentDestinationAsState().value

    Scaffold(
        topBar = {
            KJsTopBar(destination, destinationsNavigator)
        },
        content = { innerPadding ->
            val newPadding = PaddingValues.Absolute(
                innerPadding.calculateLeftPadding(LayoutDirection.Ltr),
                innerPadding.calculateTopPadding(),
                innerPadding.calculateRightPadding(LayoutDirection.Ltr),
                0.dp
            )
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(windowSizeClass)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(newPadding)
                    .padding(newPadding)
            ) {
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KJsTopBar(
    destination: DestinationSpec?,
    destinationsNavigator: DestinationsNavigator,
) {
    var displayMainMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "KJ's BMC") },
        navigationIcon = {
            when (destination) {
                HomeDestination -> {
                    // no back button here
                }

                else -> {
                    IconButton(onClick = { destinationsNavigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    displayMainMenu = !displayMainMenu
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = displayMainMenu,
                onDismissRequest = { displayMainMenu = false }
            ) {
                DropdownMenuItem(
                    enabled = true,
                    text = { Text(text = "Add Bike", style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(BikeAddDestination)
                    }
                )
                DropdownMenuItem(
                    enabled = true,
                    text = {
                        Text(
                            text = "Add Component",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(ComponentAddDestination(bikeUid = null))
                    }
                )
                DropdownMenuItem(
                    enabled = true,
                    text = {
                        Text(
                            text = "Manage Bikes / Components",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(ManageBikesAndComponentsDestination)
                    }
                )
                DropdownMenuItem(
                    enabled = true,
                    text = {
                        Text(
                            text = "Component shelf",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(ShelfDestination)
                    }
                )
                DropdownMenuItem(
                    enabled = destination != SettingsDestination,
                    text = { Text(text = "Settings", style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(SettingsDestination)
                    }
                )
                HorizontalDivider()
                DropdownMenuItem(
                    enabled = destination != AboutDestination,
                    text = {
                        Text(
                            text = "About KJs BMC",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        displayMainMenu = false
                        destinationsNavigator.navigate(AboutDestination)
                    }
                )
            }
        }
    )
}