package com.simon.storeharmonytest.ui.screens.homescreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.mappers.Products
import com.simon.storeharmonytest.ui.component.BaseScaffoldWithAppbar
import com.simon.storeharmonytest.ui.component.BodyText
import com.simon.storeharmonytest.ui.component.BodyTextSmall
import com.simon.storeharmonytest.ui.component.ChangeNavigationBarColor
import com.simon.storeharmonytest.ui.component.FilledTextField
import com.simon.storeharmonytest.ui.component.HeaderText
import com.simon.storeharmonytest.ui.component.PrimaryButton
import com.simon.storeharmonytest.ui.component.SimonIconButton
import com.simon.storeharmonytest.ui.component.SimonPrimaryIconButton
import com.simon.storeharmonytest.ui.component.TitleText
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel
import com.simon.storeharmonytest.ui.viewmodels.ProductsState
import com.simon.storeharmonytest.ui.viewmodels.SortOrder
import com.simon.storeharmonytest.ui.viewmodels.SortType

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToProduct: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToProfile: () -> Unit
) {

    val productsState = mainViewModel.products.collectAsStateWithLifecycle(ProductsState()).value
    val sortOrder = mainViewModel.sortOrder.collectAsStateWithLifecycle().value
    val sortType = mainViewModel.sortType.collectAsStateWithLifecycle().value
    val search = rememberSaveable() {
        mutableStateOf("")
    }
    val cart = mainViewModel.cart.collectAsStateWithLifecycle(initialValue = emptyList()).value
    ChangeNavigationBarColor(color = MaterialTheme.colorScheme.background,true)
    LaunchedEffect(true) {
        if (productsState.data.isNullOrEmpty()) {
            mainViewModel.getProducts()
        }
    }

    HomeScreenContent(
        productsState.copy(data =
        if (search.value.isEmpty()) productsState.data
        else productsState.data?.filter {
            it.name.lowercase().contains(search.value) ||
                    it.category.lowercase().contains(search.value)
        }),
        cartItems = cart.size,
        sortType,
        sortOrder,
        onUpdateSortOrder = { mainViewModel.updateSortOrder(it) },
        onUpdateSortType = { mainViewModel.updateSortType(it) },
        loadMore = { mainViewModel.getProducts() },
        onSearch = {
            search.value = it.lowercase()
        },
        navigateToProduct = {
            mainViewModel.onProductClicked(it)
            navigateToProduct()
        },
        navigateToCart = navigateToCart,
        navigateToProfile = navigateToProfile
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    productsState: ProductsState,
    cartItems: Int,
    sortType: SortType,
    sortOrder: SortOrder,
    onUpdateSortOrder: (SortOrder) -> Unit,
    onUpdateSortType: (SortType) -> Unit,
    loadMore: () -> Unit,
    onSearch: (String) -> Unit,
    navigateToProduct: (Products) -> Unit,
    navigateToCart: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val innerSearch = remember {
        mutableStateOf("")
    }
    BaseScaffoldWithAppbar(title = "",
        hideBackButton = true,
        actions = {
            SimonIconButton(
                onClick = { navigateToProfile() },
                icon = R.drawable.icon_profile,
            )
            Spacer(modifier = Modifier.width(10.dp))

            SimonIconButton(
                onClick = { navigateToCart() },
                icon = R.drawable.icon_cart,
                badge = if(cartItems==0) null else cartItems.toString()
            )

        },
        onBackPressed = { }) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            item(span = { GridItemSpan(2) }) {
                Column {
                    HeaderText(text = stringResource(R.string.hello))
                    BodyText(text = stringResource(R.string.welcome_to_store_harmony))
                }
            }

            item(span = { GridItemSpan(2) }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    FilledTextField(modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp),
                        hasIcon = true, icon = R.drawable.placeholdser_searc,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        maxLines = 1,
                        value = innerSearch.value, hint = "Search",
                        onChange = {
                            innerSearch.value = it
                            if (it.isEmpty()) {
                                onSearch(it)
                            }
                        })

                    Spacer(modifier = Modifier.width(10.dp))

                    SimonPrimaryIconButton(
                        onClick = { onSearch(innerSearch.value) },
                        modifier = Modifier.fillMaxHeight(),
                        icon = R.drawable.search_icon
                    )
                }
            }

            item(span = { GridItemSpan(2) }) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    SortComposable(sortOrder) {
                        onUpdateSortOrder(it)
                    }
                    SortPropertyRow(sortType) {
                        onUpdateSortType(it)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    TitleText(text = stringResource(R.string.products))
                }

            }

            if (productsState.loading && productsState.data.isNullOrEmpty()) {
                items(6) {
                    ProductsGridItem(loading = true)
                }
            } else if (productsState.error) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_error),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.FillWidth
                        )
                        HeaderText(text = stringResource(R.string.error_occurred))
                        BodyText(
                            text = stringResource(R.string.products_could_not_be_loaded_please_try_again),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        PrimaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.try_again),
                            isRounded = false
                        ) {
                            loadMore()
                        }
                    }
                }
            } else if (productsState.success && productsState.data != null) {
                if (productsState.data.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_empty),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .aspectRatio(1f),
                                contentScale = ContentScale.FillWidth
                            )
                            HeaderText(text = stringResource(R.string.no_products))
                            BodyText(
                                text = stringResource(R.string.no_products_found_in_your_store),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(productsState.data) { product ->
                        ProductsGridItem(product) {
                            navigateToProduct(product)
                        }
                    }
                    if (productsState.hasMore) {
                        item(span = { GridItemSpan(2) }) {
                            PrimaryButton(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(top = 10.dp),
                                text = stringResource(R.string.load_more),
                                loading = productsState.loading
                            ) {
                                loadMore()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SortPropertyRow(selectedType: SortType, onSelectType: (SortType) -> Unit) {

    val scroll = rememberScrollState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
    ) {

        SortChipItem(text = R.string.name, isActive = selectedType == SortType.NAME) {
            onSelectType(SortType.NAME)
        }
        SortChipItem(text = R.string.price, isActive = selectedType == SortType.PRICE) {
            onSelectType(SortType.PRICE)
        }
        SortChipItem(text = R.string.category, isActive = selectedType == SortType.CATEGORY) {
            onSelectType(SortType.CATEGORY)
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SortChipItem(text: Int, isActive: Boolean = false, onClick: () -> Unit = {}) {
    BodyText(
        text = stringResource(id = text),
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .requiredWidthIn(min = 120.dp)
            .border(
                1.dp,
                if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            )
            .combinedClickable {
                onClick()
            }
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(vertical = 11.dp, horizontal = 15.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SortComposable(currentSort: SortOrder, onSort: (SortOrder) -> Unit) {

    val isExpanded = remember {
        mutableStateOf(false)
    }

    fun onSelect(sort: SortOrder) {
        onSort(sort)
        isExpanded.value = false
    }

    val rotation = animateFloatAsState(
        targetValue = if (isExpanded.value) 180f else 0f,
        label = "rotation"
    )
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TitleText(
            text = stringResource(R.string.sort_by),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        Box() {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.shapes.medium
                    )
                    .padding(5.dp)
                    .combinedClickable() {
                        isExpanded.value = true
                    }) {
                BodyText(
                    text = stringResource(id = currentSort.text),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.icon_drop_down),
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation.value)
                )
            }

            DropdownMenu(expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false }) {

                DropdownMenuItem(text = { BodyText(text = stringResource(id = R.string.asc)) },
                    onClick = { onSelect(SortOrder.Asc) })
                DropdownMenuItem(text = { BodyText(text = stringResource(id = R.string.desc)) },
                    onClick = { onSelect(SortOrder.Desc) })
            }
        }

    }
}