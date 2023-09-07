package com.simon.storeharmonytest.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.models.UserProfile
import com.simon.storeharmonytest.ui.component.BaseScaffoldWithAppbar
import com.simon.storeharmonytest.ui.component.FilledTextField
import com.simon.storeharmonytest.ui.component.PrimaryButton
import com.simon.storeharmonytest.ui.component.ChangeNavigationBarColor
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    ChangeNavigationBarColor(MaterialTheme.colorScheme.primary,dontDispose = true)
    val _userProfile = mainViewModel.userProfile.collectAsState(initial = null)
    val userProfile = remember(_userProfile.value) {
        mutableStateOf(
            _userProfile.value ?: UserProfile(
                firstName = "",
                lastName = "",
                email = "",
                country = "",
                city = "",
                state = "",
                phone = ""
            )
        )
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    UserProfileContent(onBackPressed, userProfile.value, onSaveProfile = {
        mainViewModel.editUserProfile(userProfile.value, {
            scope.launch(Dispatchers.Main) {
                Toasty.error(context, it).show()
            }
        }) {
            scope.launch(Dispatchers.Main) {
                Toasty.success(
                    context,
                    context.getString(R.string.user_profile_updated_successfully)
                ).show()
            onBackPressed()
            }
        }
    }) {
        userProfile.value = it
    }
}

@Composable
private fun UserProfileContent(
    onBackPressed: () -> Unit,
    userProfile: UserProfile,
    onSaveProfile: () -> Unit,
    updateUserProfile: (UserProfile) -> Unit
) {

    BaseScaffoldWithAppbar(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(R.string.my_profile),
        onBackPressed = onBackPressed
    ) {

        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                FilledTextField(
                    modifier = Modifier.weight(1f),
                    value = userProfile.firstName,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    header = stringResource(R.string.header_first_name),
                    hint = stringResource(R.string.enter_frist_name),
                ) {
                    updateUserProfile(userProfile.copy(firstName = it))
                }
                Spacer(modifier = Modifier.width(15.dp))

                FilledTextField(
                    modifier = Modifier.weight(1f),
                    value = userProfile.lastName,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    header = stringResource(R.string.header_last_name),
                    hint = stringResource(R.string.enter_last_name),
                ) {
                    updateUserProfile(userProfile.copy(lastName = it))
                }
            }
            FilledTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                value = userProfile.email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                maxLines = 1,
                header = stringResource(R.string.header_email_address),
                hint = stringResource(R.string.enter_email_address),
            ) {
                updateUserProfile(userProfile.copy(email = it))
            }
            Row(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                FilledTextField(
                    modifier = Modifier.weight(1f),
                    value = userProfile.country,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    header = stringResource(R.string.header_country),
                    hint = stringResource(R.string.enter_frist_name),
                ) {
                    updateUserProfile(userProfile.copy(country = it))
                }
                Spacer(modifier = Modifier.width(15.dp))

                FilledTextField(
                    modifier = Modifier.weight(1f),
                    value = userProfile.state,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    header = stringResource(R.string.header_state),
                    hint = stringResource(R.string.state),
                ) {
                    updateUserProfile(userProfile.copy(state = it))
                }
            }
            FilledTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                value = userProfile.city,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                maxLines = 1,
                header = stringResource(R.string.header_city),
                hint = stringResource(R.string.enter_city),
            ) {
                updateUserProfile(userProfile.copy(city = it))
            }
            FilledTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                value = userProfile.phone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                maxLines = 1,
                header = stringResource(R.string.header_phone),
                hint = stringResource(R.string.enter_phone_no),
            ) {
                updateUserProfile(userProfile.copy(phone = it))
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = stringResource(R.string.save_profile),
                isRounded = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                onSaveProfile()
            }

        }

    }

}