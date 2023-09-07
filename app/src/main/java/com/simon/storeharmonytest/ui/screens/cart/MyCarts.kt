package com.simon.storeharmonytest.ui.screens.cart

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.mappers.CartProduct
import com.simon.storeharmonytest.data.models.UserProfile
import com.simon.storeharmonytest.ui.component.BaseScaffoldWithAppbar
import com.simon.storeharmonytest.ui.component.BodyText
import com.simon.storeharmonytest.ui.component.BodyTextSmall
import com.simon.storeharmonytest.ui.component.ChangeNavigationBarColor
import com.simon.storeharmonytest.ui.component.HeaderText
import com.simon.storeharmonytest.ui.component.PrimaryButton
import com.simon.storeharmonytest.ui.component.SimonIconButton
import com.simon.storeharmonytest.ui.component.TitleText
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    goToAddress: () -> Unit,
    goToConfirmed: () -> Unit,
    onBackPressed: () -> Unit
) {

    val carts = mainViewModel.cart.collectAsStateWithLifecycle(initialValue = emptyList()).value
    val profile = mainViewModel.userProfile.collectAsStateWithLifecycle(initialValue = null).value
    val loading = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ChangeNavigationBarColor(color = MaterialTheme.colorScheme.primary, true)

    CartScreenContent(carts, profile,
        deleteFromCart = {
            mainViewModel.removeItemsFromCart(it.key)
            Toasty.info(context, R.string.remove_from_cart).show()
        },
        checkOut = {
            if (profile != null) {
                mainViewModel.placeOrder(carts,
                    profile,
                    onLoading = {
                        loading.value = true
                        scope.launch(Dispatchers.Main) {
                            Toasty.success(context, R.string.loading).show()
                        }
                    },
                    onSuccess = {
                        loading.value = false
                        scope.launch(Dispatchers.Main) {
                            Toasty.success(context, R.string.items_checked_out).show()
                            goToConfirmed()
                        }
                    },
                    onError = {
                        loading.value = false
                        scope.launch(Dispatchers.Main) {
                            Toasty.error(context, R.string.error_occurred).show()
                        }
                    })
            }
        },
        loading = loading.value,
        goToAddress = {
            goToAddress()
        }) {
        onBackPressed()
    }

}

@Composable
private fun CartScreenContent(
    cart: List<CartProduct>,
    profile: UserProfile?,
    deleteFromCart: (CartProduct) -> Unit,
    checkOut: () -> Unit,
    loading: Boolean = false,
    goToAddress: () -> Unit,
    onBackPressed: () -> Unit
) {

    BaseScaffoldWithAppbar(
        title = stringResource(id = R.string.cart),
        onBackPressed = { onBackPressed() }) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if (cart.isEmpty()) {
                item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_empty),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                        HeaderText(text = stringResource(R.string.cart_empty))
                        BodyText(
                            text = stringResource(R.string.no_products_in_your_cart),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(cart) {
                    CartItem(cart = it) {
                        deleteFromCart(it)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    if (profile == null) {
                        ToDOItem(image = R.drawable.adress,
                            header = stringResource(R.string.delivery_address),
                            title = stringResource(R.string.you_haven_t_completed_your_profile),
                            description = stringResource(R.string.click_here_to_fill_in_your_details),
                            isChecked = false,
                            onDescriptionClicked = {
                                goToAddress()
                            },
                            onHeaderClicked = {
                                goToAddress()
                            })
                    } else {
                        ToDOItem(
                            image = R.drawable.adress,
                            header = stringResource(R.string.delivery_address),
                            title = "${profile.state} ${profile.country}",
                            description = profile.city,
                            isChecked = true,
                            onHeaderClicked = {
                                goToAddress()
                            }
                        )
                    }
                }

                item {
                    ToDOItem(
                        image = R.drawable.payment,
                        header = stringResource(R.string.payment_method),
                        title = stringResource(R.string.visa_classic),
                        description = stringResource(R.string._7690), isChecked = true
                    )
                }

                item {
                    Column(
                        Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        TitleText(text = stringResource(R.string.order_info))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BodyText(
                                text = stringResource(R.string.subtotal),
                                modifier = Modifier.weight(1f)
                            )
                            TitleText(
                                text = stringResource(
                                    id = R.string.price_template,
                                    (cart.map { it.quantity * it.unitPrice }.sum())
                                )
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BodyText(
                                text = stringResource(R.string.shipping),
                                modifier = Modifier.weight(1f)
                            )
                            TitleText(
                                text = stringResource(
                                    id = R.string.price_template,
                                    (cart.size * 2)
                                )
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BodyText(
                                text = stringResource(R.string.total_price),
                                modifier = Modifier.weight(1f)
                            )
                            TitleText(
                                text = stringResource(
                                    id = R.string.price_template,
                                    (cart.map { it.quantity * it.unitPrice }
                                        .sum()) + (cart.size * 2)
                                )
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    PrimaryButton(
                        text = stringResource(R.string.check_out),
                        isRounded = false,
                        enabled = profile != null && loading.not(),
                        loading = loading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        checkOut()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartItem(cart: CartProduct, deleteFromCart: (CartProduct) -> Unit) {

    ElevatedCard(
        onClick = { },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            AsyncImage(
                model = cart.imageUrl, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
            )

            Column() {
                BodyText(text = cart.name, color = MaterialTheme.colorScheme.onSurface)
                BodyText(text = cart.category, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(10.dp))
                BodyTextSmall(
                    text = stringResource(id = R.string.price_template, cart.totalPrice),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BodyText(
                        text = stringResource(id = R.string.quantity) + ": " + cart.quantity,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SimonIconButton(
                        onClick = { deleteFromCart(cart) },
                        icon = R.drawable.icon_delete
                    )
                }
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToDOItem(
    @DrawableRes image: Int, header: String, title: String, description: String, isChecked: Boolean,
    onHeaderClicked: () -> Unit = {}, onDescriptionClicked: () -> Unit = {}
) {

    Column(
        Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.combinedClickable() {
            onHeaderClicked()
        }, verticalAlignment = Alignment.CenterVertically) {
            HeaderText(
                text = header,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            AsyncImage(
                model = image, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium), contentScale = ContentScale.Crop
            )

            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                TitleText(text = title, textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(10.dp))
                BodyText(text = description,
                    modifier = Modifier.combinedClickable() {
                        onDescriptionClicked()
                    })
            }

            Icon(
                painterResource(id = if (isChecked) R.drawable.icon_check else R.drawable.icon_not_check),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }

}