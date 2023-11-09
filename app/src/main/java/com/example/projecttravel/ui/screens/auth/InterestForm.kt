package com.example.projecttravel.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.SendInterest
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.ui.screens.auth.api.interestSaveApiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun InterestForm(
    userUiState: UserUiState,
    onCompleteButtonClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    var interestErrorMsg by remember { mutableStateOf("") }

    var sightsPosition by remember { mutableFloatStateOf(5F) }
    var naturePosition by remember { mutableFloatStateOf(5F) }
    var culturePosition by remember { mutableFloatStateOf(5F) }
    var historyPosition by remember { mutableFloatStateOf(5F) }
    var foodPosition by remember { mutableFloatStateOf(5F) }
    var religionPosition by remember { mutableFloatStateOf(5F) }

    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> TextMsgErrorDialog(
                txtErrorMsg = interestErrorMsg,
                onDismiss = { isLoadingState = null })

            else -> isLoadingState = null
        }
    }

    Column (
        verticalArrangement = Arrangement.Top, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 15.dp, end = 15.dp),
    ) {

        Text(text = "name = ${userUiState.currentSignIn?.name}")
        Text(text = "email = ${userUiState.currentSignIn?.email}")
        Text(text = "password = ${userUiState.currentSignIn?.password}")

        Spacer(modifier = Modifier.height(20.dp))

        //sightsPosition
        Column {
            Slider(
                value = sightsPosition,
                onValueChange = { sightsPosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "sightsPosition")
            Text(text = "{sightsPosition = ${sightsPosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //naturePosition
        Column {
            Slider(
                value = naturePosition,
                onValueChange = { naturePosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "naturePosition")
            Text(text = "{naturePosition = ${naturePosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //culturePosition
        Column {
            Slider(
                value = culturePosition,
                onValueChange = { culturePosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "culturePosition")
            Text(text = "{culturePosition = ${culturePosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //historyPosition
        Column {
            Slider(
                value = historyPosition,
                onValueChange = { historyPosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "historyPosition")
            Text(text = "{historyPosition = ${historyPosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //foodPosition
        Column {
            Slider(
                value = foodPosition,
                onValueChange = { foodPosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "foodPosition")
            Text(text = "{foodPosition = ${foodPosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //religionPosition
        Column {
            Slider(
                value = religionPosition,
                onValueChange = { religionPosition = it },
                steps = 9,
                valueRange = 0f..10F
            )
            Text(text = "religionPosition")
            Text(text = "{religionPosition = ${religionPosition.toInt()}")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoadingState = true
                    if (userUiState.currentSignIn!= null) {
                        val sendInterest = SendInterest(
                            email = userUiState.currentSignIn.email,
                            sights = sightsPosition.toInt().toString(),
                            nature = naturePosition.toInt().toString(),
                            culture = culturePosition.toInt().toString(),
                            history = historyPosition.toInt().toString(),
                            food = foodPosition.toInt().toString(),
                            religion = religionPosition.toInt().toString(),
                            )
                        val interestDeferred =
                            async { interestSaveApiCall(sendInterest, userUiState) }
                        val interestComplete = interestDeferred.await()

                        if (interestComplete) {
                            isLoadingState = null
                            onCompleteButtonClicked()
                        } else {
                            interestErrorMsg = "저장 실패\n다시 시도해 주세요."
                            isLoadingState = false
                        }
                    }
                }
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("선호도 저장하기!")
        }
    }
}
