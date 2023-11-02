package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.SpotDto
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import java.time.LocalDate

@Composable
fun PlanCardTourWeather(
    modifier: Modifier = Modifier,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    spotDto: SpotDto,
    onDateClick: (LocalDate) -> Unit,
) {
    var isMoveAttrDialogVisible by remember { mutableStateOf(false) }
    val sortedDates = planUiState.dateToSelectedTourAttrMap.keys.sorted()

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp)
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
                        .data(spotDto.img)
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
                Text(
                    text = spotDto.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            Column (
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Blue),
                    onClick = { isMoveAttrDialogVisible = true }
                ) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = "CancelTourAttraction", tint = Color.White)
                    if (isMoveAttrDialogVisible) {
                        AlertDialog(
                            onDismissRequest = { isMoveAttrDialogVisible =false },
                            text = {
                                LazyColumn(
                                    modifier = Modifier,
                                    contentPadding = PaddingValues(5.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    items(sortedDates) { date ->
                                        Text(
                                            text = date.toString(), // 날짜를 가져와서 텍스트로 표시
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .fillMaxWidth()
                                                .clickable {
                                                    planUiState.currentPlanDate?.let { planViewModel.removeAttrByWeather(sourceDate = it, spotDtoToMove = spotDto) }
                                                    planViewModel.addAttrByWeather(destinationDate = date, spotDtoToMove = spotDto)
                                                    planViewModel.setCurrentPlanDate(date)
                                                    onDateClick(date)
                                                    isMoveAttrDialogVisible =false
                                                }
                                        )
                                    }
                                }
                            },
                            confirmButton = { },
                        )
                    }
                }
            }
        }
    }
}
