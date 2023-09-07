package com.simon.storeharmonytest.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.simon.storeharmonytest.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simon.storeharmonytest.ui.component.BodyText
import com.simon.storeharmonytest.ui.component.ChangeNavigationBarColor
import com.simon.storeharmonytest.ui.component.HeaderText
import com.simon.storeharmonytest.ui.component.PrimaryButton
import com.simon.storeharmonytest.ui.component.SecondaryButton
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel


@Composable
fun WelcomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onGoToHome: () -> Unit,
    onGoToProfile: () -> Unit
) {

    val userProfile = mainViewModel.userProfile.collectAsStateWithLifecycle(initialValue = null)
    ChangeNavigationBarColor(color = MaterialTheme.colorScheme.primary,true)
    LaunchedEffect(key1 = userProfile.value ){
        if(userProfile.value!=null){
            onGoToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.welcome_bg),
                contentScale = ContentScale.Crop
            )
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            HeaderText(text = stringResource(R.string.welcome))
            Spacer(modifier = Modifier.height(5.dp))
            BodyText(
                text = stringResource(R.string.welcome_description),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                SecondaryButton(text = stringResource(R.string.skip), modifier = Modifier.weight(1f)) {
                    onGoToHome()
                }
                PrimaryButton(text = stringResource(R.string.add_profile), modifier = Modifier.weight(1f)) {
                    onGoToProfile()
                }

            }

        }

    }

}