package com.example.projecttravel.ui.login.Forms

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.ui.login.data.Form
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignForm (
    onCancelButtonClicked: () -> Unit = {},
) {
    Surface {
        var signCredentials by remember { mutableStateOf(SignCredentials()) }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            SignEmailField(
                value = signCredentials.email,
                onChange = { data -> signCredentials = signCredentials.copy(email = data) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            SignIdField(
                value = signCredentials.name,
                onChange = { data -> signCredentials = signCredentials.copy(name = data) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            SignPasswordField(
                value = signCredentials.pwd,
                onChange = { data -> signCredentials = signCredentials.copy(pwd = data) },
                submit = {
                    if (!checkSignCredentials(signCredentials, context)) signCredentials = SignCredentials()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val sendForm = Form(
                        email = signCredentials.email,
                        name = signCredentials.name, // Consider changing the names here if needed
                        password = signCredentials.pwd, // Consider changing the names here if needed
                        type = "form" // Provide the appropriate type here
                    )
                    writeForm(sendForm)
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign in")
            }
        }
    }
}


fun checkSignCredentials(signCredentials: SignCredentials, context: Context): Boolean {
    if (signCredentials.isNotEmpty()) {
        return true
    } else {
        Toast.makeText(context, "모든 항목을 채우세요", Toast.LENGTH_SHORT).show()
        return false
    }
}

data class SignCredentials(
    var email: String = "",
    var name: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return name.isNotEmpty() && pwd.isNotEmpty() && email.isNotEmpty()
    }
}

@Composable
fun SignEmailField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    placeholder: String = "Enter your Email"
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
fun SignIdField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Name",
    placeholder: String = "Enter your Name"
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
fun SignPasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
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

/** HTTP Request & Response ====================*/
fun writeForm(sendForm: Form) {
    val call = RetrofitBuilderJson.travelJsonApiService.setFormResponse(sendForm)
    call.enqueue(
        object : Callback<String> { // 비동기 방식 통신 메소드
            override fun onResponse(
                // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>,
            ) {
                if (response.isSuccessful) { // 응답 잘 받은 경우
                    Log.d("SignInSignInSignInSignInSignInSignIn", response.body().toString())

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("SignInSignInSignInSignInSignInSignIn", "FAILURE")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                t.localizedMessage?.let { Log.d("SignInSignInSignInSignInSignInSignIn", it) }
            }
        }
    )
}
