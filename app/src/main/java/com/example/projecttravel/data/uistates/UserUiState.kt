package com.example.projecttravel.data.uistates

import com.example.projecttravel.R
import com.example.projecttravel.model.PlansDataRead
import com.example.projecttravel.model.SendSignIn
import com.example.projecttravel.model.SpotDtoResponseRead
import com.example.projecttravel.model.UserResponse
import java.time.LocalDate

data class UserUiState(
    val currentLogin: UserResponse? = null,
    val currentSignIn: SendSignIn? =null,

    val checkLikeUsers: List<UserResponse> = emptyList(),
    val checkOtherUser: UserResponse? = null,
    val checkMyPlanList: List<PlansDataRead> = emptyList(),
    val checkMyPageTrip: PlansDataRead? = null,

    val currentMyPlanDate: String? = null,

    val currentSelectedUserTab: Int = R.string.userTabMenuBoard,

    val previousScreenWasPageOneA: Boolean = false,

    val isBackHandlerClick: Boolean = false,
)
