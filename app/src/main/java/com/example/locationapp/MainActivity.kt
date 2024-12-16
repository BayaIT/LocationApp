package com.example.locationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationAppTheme {
                val locationViewModel: LocationViewModel = viewModel()
                val locationUtils = LocationUtils(this)

                LocationDisplay(
                    locationUtils = locationUtils,
                    viewModel = locationViewModel
                )
            }
        }
    }
}