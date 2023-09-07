package com.simon.storeharmonytest.ui.screens.viewProducts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.mappers.Products
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

@Composable
fun ViewProductsScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    goToCart: () -> Unit,
    onBackPressed: () -> Unit
) {
    val quantity = rememberSaveable() {
        mutableIntStateOf(1)
    }
    val context = LocalContext.current
    val productToView = mainViewModel.productToView
    ChangeNavigationBarColor(color = MaterialTheme.colorScheme.primary,true)
    productToView?.let {
        ViewProductsHome(products = it,
            quantity.intValue,
            onIncrement = {
                if (quantity.intValue < productToView.unitsInStock) {
                    quantity.intValue += 1
                } else {
                    Toasty.info(context, R.string.out_of_stock).show()
                }
            },
            onDecrement = {
                if (quantity.intValue > 1) {
                    quantity.intValue -= 1
                }
            },
            goToCart = goToCart,
            addToCart = {
                mainViewModel.addToCart(productToView, quantity.intValue)
                Toasty.success(context, R.string.added_to_cart).show()
                onBackPressed()
            }) {
            onBackPressed()
        }
    } ?: run {
        onBackPressed()
    }
}

val sizes = listOf("S", "M", "L", "XL", "2XL")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewProductsHome(
    products: Products,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    addToCart: () -> Unit,
    goToCart: () -> Unit,
    onBackPressed: () -> Unit
) {

    BaseScaffoldWithAppbar(
        title = "",
        iconsBackgroundTint = MaterialTheme.colorScheme.background,
        actions = {
            SimonIconButton(
                onClick = { goToCart() }, icon = R.drawable.icon_cart,
                iconsBackgroundTint = MaterialTheme.colorScheme.background
            )
        },
        onBackPressed = onBackPressed
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                model = products.imageUrl, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column(Modifier.weight(1f)) {
                        BodyTextSmall(
                            text = products.name, maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                        )
                        HeaderText(text = products.category)
                    }

                    Column(Modifier.padding(start = 10.dp)) {
                        BodyTextSmall(
                            text = stringResource(id = R.string.price),
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        HeaderText(
                            text = stringResource(
                                id = R.string.price_template,
                                products.unitPrice
                            )
                        )
                    }

                }

                Column {
                    TitleText(text = stringResource(R.string.size))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        sizes.forEach {
                            Box(
                                modifier = Modifier
                                    .requiredWidthIn(min = 50.dp)
                                    .aspectRatio(1f)
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.shapes.medium
                                    )
                                    .padding(10.dp)
                            ) {
                                TitleText(
                                    text = it,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.Center)

                                )
                            }
                        }

                    }
                }

                Column {
                    TitleText(text = stringResource(R.string.description))
                    BodyText(text = products.description)
                }

                Column {
                    TitleText(text = stringResource(R.string.quantity))
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        SimonIconButton(
                            onClick = {
                                onIncrement()
                            },
                            icon = R.drawable.baseline_keyboard_arrow_up_24
                        )
                        HeaderText(text = quantity.toString())
                        SimonIconButton(
                            onClick = { onDecrement() },
                            icon = R.drawable.baseline_keyboard_arrow_down_24
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    ) {
                        TitleText(text = stringResource(R.string.total_price))
                        BodyTextSmall(
                            text = stringResource(R.string.with_vat_sd),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    TitleText(
                        text = stringResource(
                            id = R.string.price_template,
                            (products.unitPrice * quantity)
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))


            PrimaryButton(
                text = stringResource(R.string.add_to_cart),
                modifier = Modifier.fillMaxWidth(),
                isRounded = false
            ) {
                addToCart()

            }

        }

    }
}