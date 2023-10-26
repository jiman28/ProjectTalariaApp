package com.example.projecttravel.ui.login.Forms

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.MainActivity
import com.example.projecttravel.ui.login.api.LoginApiCall
import com.example.projecttravel.ui.login.data.Form
import com.example.projecttravel.ui.login.data.User
import com.example.projecttravel.ui.login.data.UserViewModel
import com.example.projecttravel.ui.login.datastore.App
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.LoginErrorDialog
import com.example.projecttravel.ui.screens.selection.selectapi.getDateToWeather
import com.example.projecttravel.ui.screens.selection.selectdialogs.convertToSpotDtoResponses
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LoginForm(
    onNextButtonClicked: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
) {
    // DataStoreModule을 사용하여 데이터 스토어로부터 pwd 값을 가져옴
//    val dataStoreModule = App.getInstance().getDataStore()
//    val savedEmail by dataStoreModule.email.collectAsState("")
//    val savedPwd by dataStoreModule.pwd.collectAsState("")

    val scope = rememberCoroutineScope()
    var credentials by remember { mutableStateOf(Credentials()) }
    val context = LocalContext.current

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }

    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog( onDismissAlert = { isLoadingState = null } )
            false -> LoginErrorDialog( onDismissAlert = { isLoadingState = null } )
            else -> isLoadingState = null
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        LoginField(
            value = credentials.email,
            onChange = { data -> credentials = credentials.copy(email = data) },
            modifier = Modifier.fillMaxWidth()
        )
        PasswordField(
            value = credentials.pwd,
            onChange = { data -> credentials = credentials.copy(pwd = data) },
            submit = {
                if (!checkCredentials(credentials, context)) credentials = Credentials()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        LabeledCheckbox(
            label = "Remember Me",
            onCheckChanged = {
                credentials = credentials.copy(remember = !credentials.remember)
            },
            isChecked = credentials.remember
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
//                if (credentials.remember) {
//                    CoroutineScope(Dispatchers.Main).launch{
//                        App.getInstance().getDataStore().setEmail(credentials.email)
//                        App.getInstance().getDataStore().setPwd(credentials.pwd)
//                        Log.d("xxxxxxxxxxxxxxxxxxxx", App.getInstance().getDataStore().email.toString())
//                        Log.d("xxxxxxxxxxxxxxxxxxxx", App.getInstance().getDataStore().pwd.toString())
//                    }
//                }
                scope.launch {
                    isLoadingState = true
                    val sendUser = User(
                        email = credentials.email,
                        password = credentials.pwd, // Consider changing the names here if needed
                    )
                    val userDeferred = async { LoginApiCall(sendUser) }
                    val isUserComplete = userDeferred.await()
                    if (isUserComplete) {
                        userViewModel.setLoginEmail(credentials.email)
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    } else {
                        isLoadingState = false
                    }
                }
            },
            enabled = credentials.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onNextButtonClicked() },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign in")
        }
    }
}

fun checkCredentials(creds: Credentials, context: Context): Boolean {
    return if (creds.isNotEmpty() && creds.email == "admin") {
        true
    } else {
        Toast.makeText(context, "잘못된 형식입니당", Toast.LENGTH_SHORT).show()
        false
    }
}

data class Credentials(
    var email: String = "",
    var pwd: String = "",
    var remember: Boolean = false,
) {
    fun isNotEmpty(): Boolean {
        return email.isNotEmpty() && pwd.isNotEmpty()
    }
}

@Composable
fun LabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean,
) {
    Row(
        Modifier
            .clickable(
                onClick = onCheckChanged
            )
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(Modifier.size(6.dp))
        Text(label)
    }
}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "E-mail",
    placeholder: String = "Enter your E-mail",
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password",
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
