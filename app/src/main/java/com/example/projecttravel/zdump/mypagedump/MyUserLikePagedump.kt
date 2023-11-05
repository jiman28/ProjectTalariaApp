package com.example.projecttravel.zdump.mypagedump

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.unit.dp
//import com.example.projecttravel.R
//import com.example.projecttravel.data.uistates.UserUiState
//import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
//
//@Composable
//fun MyUserLikePageDump(
//    userUiState: UserUiState,
//    userViewModel: ViewModelUser,
//) {
//    LazyColumn(
//        modifier = Modifier,
//        verticalArrangement = Arrangement.spacedBy(24.dp),
//    ) {
//        items(
//            items = userUiState.checkLikeUsers,
//            key = { people ->
//                people.id
//            }
//        ) { people ->
//            Column {
//                Text(text = "1. email = ${people.email}")
//                Text(text = "2. planName = ${people.name}")
//                Text(text = "3. endDay = ${people.id}")
//            }
//            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        }
//    }
//
//
//}