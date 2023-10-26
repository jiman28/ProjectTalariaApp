package com.example.projecttravel.ui.login.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_datastore")
//
//object UserPreferencesKeys {
//    val EMAIL = stringPreferencesKey("user_email")
//    val PASSWORD = stringPreferencesKey("user_password")
//}
//
//// 이메일 저장
//suspend fun saveUserEmail(context: Context, email: String) {
//    context.dataStore.edit { preferences ->
//        preferences[UserPreferencesKeys.EMAIL] = email
//    }
//}
//
//// 비밀번호 저장
//suspend fun saveUserPassword(context: Context, password: String) {
//    context.dataStore.edit { preferences ->
//        preferences[UserPreferencesKeys.PASSWORD] = password
//    }
//}
//
//// 이메일 불러오기
//suspend fun getUserEmail(context: Context): String {
//    val email = context.dataStore.data.map { preferences ->
//        preferences[UserPreferencesKeys.EMAIL] ?: ""
//    }.first()
//    return email
//}
//
//// 비밀번호 불러오기
//suspend fun getUserPassword(context: Context): String {
//    val password = context.dataStore.data.map { preferences ->
//        preferences[UserPreferencesKeys.PASSWORD] ?: ""
//    }.first()
//    return password
//}
//
//// 이메일과 비밀번호를 사용하여 자동으로 로그인
//suspend fun autoLogin(context: Context) {
//    val email = getUserEmail(context)
//    val password = getUserPassword(context)
//}
