package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.model.select.TourAttractionSearchInfo

@Composable
fun PlanTourAttrCard(
    modifier: Modifier = Modifier,
    tourAttractionAll: TourAttractionAll,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(
                        when (tourAttractionAll) {
                            is TourAttractionInfo -> tourAttractionAll.imageP
                            is TourAttractionSearchInfo -> tourAttractionAll.img
                            else -> {}
                        }
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_image_country),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = when (tourAttractionAll) {
                    is TourAttractionInfo -> tourAttractionAll.placeName
                    is TourAttractionSearchInfo -> tourAttractionAll.name
                    else -> "몰루"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}
