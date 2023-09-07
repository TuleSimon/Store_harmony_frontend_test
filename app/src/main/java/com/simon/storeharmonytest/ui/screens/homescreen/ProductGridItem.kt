package com.simon.storeharmonytest.ui.screens.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.mappers.Products
import com.simon.storeharmonytest.ui.component.BodyText
import com.simon.storeharmonytest.ui.component.BodyTextSmall
import com.simon.storeharmonytest.ui.component.BodyTextSmaller
import com.simon.storeharmonytest.ui.component.TitleText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsGridItem(
    products: Products? = null, loading: Boolean = false,
    onClick: (Products) -> Unit = {}
) {

    val imageLoading = rememberSaveable() {
        mutableStateOf(true)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable() {
            products?.let { onClick(it) }
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f, true)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
        ) {

            if (imageLoading.value || loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 1.dp, color = MaterialTheme.colorScheme.primary
                )
            }

            AsyncImage(
                model = products?.imageUrl, contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                error = if (loading) null else painterResource(id = R.drawable.baseline_image_not_supported_24),
                contentScale = ContentScale.Crop,
                onLoading = {
                    imageLoading.value = true
                },
                onError = {
                    imageLoading.value = false
                },
                onSuccess = {
                    imageLoading.value = false
                }
            )
        }

        BodyText(
            color = MaterialTheme.colorScheme.onSurface,
            text = products?.name ?: "dummy",
            modifier = Modifier
                .fillMaxWidth()
                .simonPlaceHolder(loading)
        )
        BodyTextSmall(
            text = products?.category ?: "description",
            modifier = Modifier
                .fillMaxWidth()
                .simonPlaceHolder(loading)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(
                text = stringResource(
                    R.string.price_template,
                    products?.unitPrice?.toString() ?: "99"
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .simonPlaceHolder(loading)
            )
            BodyTextSmaller(
                text = stringResource(R.string.available, products?.unitsInStock ?: 0),
                modifier = Modifier
                    .weight(1f)
                    .simonPlaceHolder(loading)
            )
        }


    }

}

@Composable
fun Modifier.simonPlaceHolder(loading: Boolean): Modifier {
    return placeholder(
        loading, MaterialTheme.colorScheme.surface,
        MaterialTheme.shapes.medium, highlight = PlaceholderHighlight.shimmer()
    )
}