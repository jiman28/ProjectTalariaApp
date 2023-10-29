package com.example.projecttravel.ui.screens.login.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class DataStore {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_setting")
        val emailKey = stringPreferencesKey("USER_EMAIL")           //사용자의 이메일
        val pwdKey = stringPreferencesKey("USER_PWD")               //사용자의 패스워드
//        val rememberKey = booleanPreferencesKey("USER_REMEMBER")    //사용자 체크 여부
    }
}