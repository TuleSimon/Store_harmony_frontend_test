package com.simon.storeharmonytest.ui.screens.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.ui.component.BaseScaffoldWithAppbar
import com.simon.storeharmonytest.ui.component.BodyText
import com.simon.storeharmonytest.ui.component.HeaderText
import com.simon.storeharmonytest.ui.component.PrimaryButton

@Composable
fun OrderConfirmed(goHome: () -> Unit, goBack: () -> Unit) {
    BaseScaffoldWithAppbar(title = "", onBackPressed = { goBack() }) {

        Column(
            Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.order_confirmed),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(20.dp))
            HeaderText(
                text = stringResource(R.string.order_confirmed),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            BodyText(
                text = stringResource(R.string.order_confirmed_desc),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.continue_shopping),
                isRounded = false
            ) {
                goHome()
            }
        }

    }
}