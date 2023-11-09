package com.example.projecttravel.ui.screens.infomeplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.SpotDtoResponseRead
import com.example.projecttravel.data.viewmodels.PlanViewModel
import com.example.projecttravel.data.viewmodels.UserViewModel

@Composable
fun SelectedUserPlanDateInfo(
    allUserAttrList: List<SpotDtoResponseRead>,
    userUiState: UserUiState,
    planViewModel: PlanViewModel,
    onRouteClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
        horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
    ) {
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(3.dp),
        ) {
            Text(
                text = userUiState.currentMyPlanDate.toString(),
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Column (
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .padding(3.dp),
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp)),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Blue,
//                            contentColor = Color.White
//                        ),
                onClick = {
                    planViewModel.setGpsPage(allUserAttrList.find { it.date == userUiState.currentMyPlanDate.toString() })
                    onRouteClicked()
                }
            ) {
                Text(text = "지도 확인")
            }
        }
    }

}
