package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StandardScaffold(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingAddButton(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
