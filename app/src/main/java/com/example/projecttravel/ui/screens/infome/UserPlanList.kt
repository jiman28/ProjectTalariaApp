package com.example.projecttravel.ui.screens.infome

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.model.PlansDataRead
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.infome.infodialog.DeletePlanDialog
import com.example.projecttravel.ui.screens.plantrip.plandialogs.SavePlanDialog

@Composable
fun UserPlanList(
    navController: NavHostController,
    filteredPlanList: List<PlansDataRead>?,
) {
    var isDeletePlanDialog by remember { mutableStateOf(false) }
    var selectedPlanIdToDelete by remember { mutableStateOf<String>("") }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog( onDismiss = { isLoadingState = null } )
            else -> isLoadingState = null
        }
    }

    if (!filteredPlanList.isNullOrEmpty()) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(
                items = filteredPlanList,
                key = { userPlan -> userPlan.id }
            ) { userPlan ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {

                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(userPlan.plans[0].list[0].img)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                error = painterResource(id = R.drawable.no_image_country),
                                placeholder = painterResource(id = R.drawable.loading_img)
                            )
                        }
                        Column (
                            modifier = Modifier.weight(3f)
                        ) {
                            Column {
                                Text(
                                    text = userPlan.planName,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R.dimen.padding_medium)),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Column {
                                Text(
                                    text = "${userPlan.startDay} ~ ${userPlan.endDay}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R.dimen.padding_medium)),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.Blue),
                                onClick = {
                                    isDeletePlanDialog = true
                                    selectedPlanIdToDelete = userPlan.id
                                    Log.d("jiman=planId=DeletePlanlist", userPlan.id)
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.Cancel, contentDescription = "CancelTourAttraction", tint = Color.White)

                            }
                        }
                    }
                }
            }
        }
    } else {
        NoPlansFoundScreen()
    }

    if (isDeletePlanDialog && selectedPlanIdToDelete != "") {
        DeletePlanDialog(
            planId = selectedPlanIdToDelete,
            onPlanDeleteClicked = { navController.navigate(TravelScreen.Page1A.name) },
            onDismiss = {
                isDeletePlanDialog = false
                selectedPlanIdToDelete = ""
            },
            onLoadingStarted = {
                isLoadingState = true
            },
            onErrorOccurred = {
                isLoadingState = false
                selectedPlanIdToDelete = ""
            },
        )
    }
}

// 계획에 글이 없을 시
@Composable
fun NoPlansFoundScreen(
){
    Text(text = stringResource(R.string.noPlansFound))
}
