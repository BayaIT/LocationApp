package com.example.locationapp

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel
) {
    val context = LocalContext.current
    val location = viewModel.location.value
    val address = location?.let { locationUtils.reverseGeocodeLocation(it) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (coarseGranted && fineGranted) {
        } else {
            Toast.makeText(context, "Location permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text("Address: ${location.latitude}, ${location.longitude}\n$address")
        } else {
            Text("Location not available")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION
            val finePermission = Manifest.permission.ACCESS_FINE_LOCATION

            if (context.checkSelfPermission(coarsePermission) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(finePermission) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                requestPermissionLauncher.launch(arrayOf(coarsePermission, finePermission))
            }
        }) {
            Text("Get Location")
        }
    }
}