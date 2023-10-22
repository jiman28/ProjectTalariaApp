package com.example.projecttravel.ui.screens.searchplacegps

import com.google.android.gms.maps.model.LatLng

data class SearchedPlace(
    val name: String? = null,
    val address: String? = null,
    val latLng: LatLng? = null,
)
