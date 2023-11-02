package com.example.projecttravel.zdump

//@Composable
//fun PlanTourDateRandomCard(
//    date: LocalDate,
//    planUiState: PlanUiState,
//    planViewModel: ViewModelPlan,
//    weatherResponseGet: WeatherResponseGet?,
//    onClick: (LocalDate) -> Unit, // 클릭 시 호출될 콜백 함수
//) {
//    DropTarget<SpotDto>(
//        modifier = Modifier
//    ) { isInBound, spotDto ->
//        val bgColor = if (isInBound) Color.Blue else Color.White
//        // Handle drop action
//        if (isInBound) {
//            if (spotDto != null) {
//                planUiState.currentPlanDate?.let { planViewModel.removeAttrByRandom(sourceDate = it, spotDtoToMove = spotDto) }
//                planViewModel.addAttrByRandom(destinationDate = date, spotDtoToMove = spotDto)
//                onClick(date)
////                planViewModel.setCurrentPlanDate(date)
//            }
//        }
//        Card(
//            shape = RoundedCornerShape(24.dp),
//            modifier = Modifier
//                .padding(8.dp)
//                .clickable {
//                    onClick(date) // 클릭 시 해당 날짜를 콜백 함수로 전달
//                    planViewModel.setCurrentPlanDate(date)
//                },
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(6.dp)
//                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
//                    .width(width = 120.dp)
//                    .height(200.dp)
//                    .background(bgColor, RoundedCornerShape(16.dp)),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // 날짜 표시
//                Text(
//                    text = date.toString(),
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(3.dp)
//                )
//                if (weatherResponseGet != null) {
//                    AsyncImage(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(8.dp)),
//                        model = ImageRequest.Builder(context = LocalContext.current)
//                            .data(
//                                weatherResponseGet.icon
//                            )
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        error = painterResource(id = R.drawable.no_image_country),
//                        placeholder = painterResource(id = R.drawable.loading_img)
//                    )
//                } else {
//                    Text(
//                        text = "현재 데이터 없음",
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(3.dp)
//                    )
//                }
//            }
//        }
//    }
//}
