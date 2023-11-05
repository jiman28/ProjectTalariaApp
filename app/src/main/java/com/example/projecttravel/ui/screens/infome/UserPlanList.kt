package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.viewmodels.user.PlanListUiState

@Composable
fun UserPlanList(
    planListUiState: PlanListUiState.PlanListSuccess,
    contentPadding: PaddingValues,
) {

    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 15.dp, end = 15.dp),
    ) {
        planListUiState.planList.forEach { userPlan ->
            Text(text = "1. email = ${userPlan.email}")
            Text(text = "2. planName = ${userPlan.planName}")
            Text(text = "3. endDay = ${userPlan.endDay}")
            Text(text = "4. startDay = ${userPlan.startDay}")
            Divider(thickness = 1.dp)
            Text(text = "5. List<SpotDto>")
            userPlan.plans.forEach { plan ->
                Text(text = "ㄴ Date: ${plan.date}")
                plan.list.forEach { spot ->
                    Text(text = "ㄴㄴ Name: ${spot.name}")
                }
                Divider(thickness = 1.dp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        }
    }

//    LazyColumn(
//        modifier = Modifier,
//        contentPadding = contentPadding,
//        verticalArrangement = Arrangement.spacedBy(24.dp),
//    ) {
//        items(
//            items = planListUiState.planList,
//            key = { userPlan ->
//                userPlan.email    // LazyColumn 은 key 값 이 절대 중복되어서는 안된다!!!
//            }
//        ) { userPlan ->
//            Column {
//                Text(text = "1. email = ${userPlan.email}")
//                Text(text = "2. planName = ${userPlan.planName}")
//                Text(text = "3. endDay = ${userPlan.endDay}")
//                Text(text = "4. startDay = ${userPlan.startDay}")
//                Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
//                Text(text = "5. List<SpotDto>")
//                userPlan.plans.forEach {
//                    Text(text = "ㄴ날짜date = ${it.date}")
//                    it.list.forEach {
//                        Text(text = "ㄴㄴ관광지이름name = ${it.name}")
//                    }
//                    Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
//                }
//            }
//            Spacer(modifier = Modifier.padding(5.dp))
//            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        }
//    }

}
