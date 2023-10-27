package com.example.projecttravel.zdump.dtsample

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
//import com.example.projecttravel.zdump.dtsample.SettingDataStore.Companion.dataStore
//import com.example.projecttravel.zdump.dtsample.SettingDataStore.Companion.ignore_duplicated_key
//import com.example.projecttravel.zdump.dtsample.SettingDataStore.Companion.ignore_no_content_key

//@Composable
//fun SettingPage() {
//    val dataStore = (LocalContext.current).dataStore
//
//    val ignoreNoContentState = remember { mutableStateOf(false) }
//    val ignoreNoDuplicatedState = remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        dataStore.data.collect { preferences ->
//            ignoreNoContentState.value = preferences[ignore_no_content_key] ?: false
//            ignoreNoDuplicatedState.value = preferences[ignore_duplicated_key] ?: false
//        }
//    }
//
//    LaunchedEffect(ignoreNoContentState.value) {
//        dataStore.edit { preferences ->
//            preferences[ignore_no_content_key] = ignoreNoContentState.value
//        }
//    }
//
//    LaunchedEffect(ignoreNoDuplicatedState.value) {
//        dataStore.edit { preferences ->
//            preferences[ignore_duplicated_key] = ignoreNoDuplicatedState.value
//        }
//    }
//
//    Checkbox(
//        checked = ignoreNoContentState.value,
//        onCheckedChange = { checked ->
//            run {
//                ignoreNoContentState.value = checked
//            }
//        }
//    )
//}
