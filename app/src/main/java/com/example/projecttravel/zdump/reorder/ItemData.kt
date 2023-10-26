package com.example.projecttravel.zdump.reorder

import com.example.projecttravel.ui.screens.selection.selectapi.SpotDto

data class ItemData(
    val keys: String,
    val attrs: SpotDto.Companion,
    val isLocked: Boolean = false,
)