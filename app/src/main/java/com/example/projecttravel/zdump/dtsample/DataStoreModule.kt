package com.example.projecttravel.zdump.dtsample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//class DataStoreModule(private val context : Context){

//    //preferences DataStore 생성
//    //DataStore를 싱글톤으로 관리하기 위해 파일 최상단에 배치, 이렇게 하면 DataStore를 하나만 생성할 수 있음
////    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")
//    private val Context.datastore by preferencesDataStore(name = "datastore")
//
//    private val emailKey = stringPreferencesKey("USER_EMAIL") //사용자의 이메일
//    private val pwdKey = stringPreferencesKey("USER_PWD") //사용자의 패스워드
//    private val loginTypeKey = intPreferencesKey("LOGIN_TYPE") //로그인 여부
//
////    val email: Flow<String> = context.dataStore.data
////        .map { preferences ->
////            // No type safety.
////            preferences[emailKey] ?: 0
////        }
////
////    suspend fun setEmail(email : String){
////        context.datastore.edit{
////            it[emailKey] = email
////        }
////    }
//
//
//    val email : Flow<String> = context.datastore.data
//        .catch{ exception ->
//            if(exception is IOException){
//                emit(emptyPreferences())
//            }else{
//                throw exception
//            }
//        }.map{
//            it[emailKey] ?: ""
//        }
//
//    suspend fun setEmail(email : String){
//        context.datastore.edit{
//            it[emailKey] = email
//        }
//    }
//
//    val pwd : Flow<String> = context.datastore.data
//        .catch{ exception ->
//            if(exception is IOException){
//                emit(emptyPreferences())
//            }else{
//                throw exception
//            }
//        }.map{
//            it[pwdKey] ?: ""
//        }
//
//    suspend fun setPwd(pwd : String){
//        context.datastore.edit{
//            it[pwdKey] = pwd
//        }
//    }
//}

