package com.example.projecttravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.projecttravel.ui.login.LoginSreenHome
import com.example.projecttravel.ui.theme.ProjectTravelTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ProjectTravelTheme {
                LoginSreenHome()
            }
        }
    }
}
