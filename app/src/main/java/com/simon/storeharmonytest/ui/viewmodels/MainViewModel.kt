package com.simon.storeharmonytest.ui.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.storeharmonytest.R
import com.simon.storeharmonytest.data.entities.CartEntity
import com.simon.storeharmonytest.data.mappers.CartProduct
import com.simon.storeharmonytest.data.mappers.Products
import com.simon.storeharmonytest.data.mappers.toCartProduct
import com.simon.storeharmonytest.data.models.UserProfile
import com.simon.storeharmonytest.data.sources.network.NetworkResponse
import com.simon.storeharmonytest.repositories.NetworkRepository
import com.simon.storeharmonytest.repositories._LocalRepository
import com.simon.storeharmonytest.utils.DEFAULT_SIZE
import com.simon.storeharmonytest.utils.UserProfileInputChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: _LocalRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {

    var productToView: Products? = null
    val userProfile = localRepository.getUserProfile()

    private val _sortType = MutableStateFlow(SortType.NONE)
    val sortType: StateFlow<SortType>
        get() = _sortType

    private val _sortOrder = MutableStateFlow<SortOrder>(SortOrder.Desc)
    val sortOrder: StateFlow<SortOrder>
        get() = _sortOrder

    val cart = localRepository.getCart().map { it.map { _item -> _item.toCartProduct() } }

    private val entireSortFlow = _sortOrder.combine(_sortType) { order, type ->
        return@combine Pair(order, type)
    }

    private val _products = MutableStateFlow(ProductsState())
    private val _products2: Flow<ProductsState>
        get() = _products.combine(entireSortFlow) { products, sort ->
            return@combine if (products.data.isNullOrEmpty()) products
            else if (sort.first == SortOrder.Asc) products.copy(data = products.data.sortedBy {
                when (sort.second) {
                    SortType.NAME -> it.name
                    SortType.PRICE -> it.unitPrice.toString()
                    SortType.CATEGORY -> it.category
                    else -> it.id.toString()

                }
            }) else products.copy(data = products.data.sortedByDescending {
                when (sort.second) {
                    SortType.NAME -> it.name
                    SortType.PRICE -> it.unitPrice.toString()
                    SortType.CATEGORY -> it.category
                    else -> it.id.toString()
                }
            })

        }

    val products: Flow<ProductsState>
        get() = _products2

    fun updateSortType(type: SortType) {
        _sortType.update { type }
    }

    fun updateSortOrder(order: SortOrder) {
        _sortOrder.update { order }
    }

    fun onProductClicked(product: Products) {
        productToView = product
    }

    fun addToCart(product: Products, quantity: Int) {
        viewModelScope.launch {
            localRepository.addToCart(
                CartEntity(
                    category = product.category,
                    description = product.description,
                    id = product.id,
                    imageUrl = product.imageUrl,
                    name = product.name,
                    sku = product.sku,
                    unitPrice = product.unitPrice,
                    unitsInStock = product.unitsInStock,
                    quantity = quantity,
                    totalPrice = quantity * product.unitPrice
                )
            )
        }
    }

    fun getProducts() = viewModelScope.launch(Dispatchers.Default) {
        networkRepository.getProducts(_products.value.page, DEFAULT_SIZE).collect {
            val loading = it is NetworkResponse.Loading
            val error = it is NetworkResponse.Error
            val success = it is NetworkResponse.Success
            val data = if (it is NetworkResponse.Success) it.data else null
            val hasMore = if (data != null) data.size == DEFAULT_SIZE else false
            val page =
                if (success && data != null) _products.value.page + 1 else _products.value.page
            val oldData = _products.value.data ?: emptyList()
            val newData =
                if (success) data?.toMutableList()?.also { it.addAll(oldData) } else oldData

            _products.update { ProductsState(newData, page, hasMore, loading, error, success) }
        }
    }


    fun editUserProfile(userProfile: UserProfile, onError: (Int) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            val inputChecker = UserProfileInputChecker(userProfile)
            if (inputChecker.isFirstNameValid().not()) {
                onError(R.string.invalid_first_name)
                return@launch
            } else if (inputChecker.isLastNameValid().not()) {
                onError(R.string.invalid_last_name)
                return@launch
            } else if (inputChecker.isEmailValid().not()) {
                onError(R.string.invalid_email)
                return@launch
            } else if (inputChecker.isCountryValid().not()) {
                onError(R.string.invalid_country)
                return@launch
            } else if (inputChecker.isStateValid().not()) {
                onError(R.string.invalid_state)
                return@launch
            } else if (inputChecker.isCityValid().not()) {
                onError(R.string.invalid_city)
                return@launch
            } else if (inputChecker.isPhoneValid().not()) {
                onError(R.string.invalid_phone_number)
                return@launch
            } else {
                localRepository.editUserProfile(userProfile)
                onSuccess()
            }

        }
    }

    fun removeItemsFromCart(vararg ids: Int) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.removeFromCart(ids.toList())
    }

    fun removeItemsFromCart(ids: List<Int>) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.removeFromCart(ids)
    }

    fun placeOrder(
        items: List<CartProduct>, userProfile: UserProfile,
        onLoading: () -> Unit, onError: () -> Unit, onSuccess: () -> Unit
    ) =
        viewModelScope.launch(Dispatchers.Default) {
            networkRepository.placeOrder(userProfile, *items.toTypedArray()).collect {
                val loading = it is NetworkResponse.Loading
                val error = it is NetworkResponse.Error
                val success = it is NetworkResponse.Success
                if (loading) {
                    onLoading()
                }
                if (error) {
                    onError()
                }
                if (success) {
                    onSuccess()
                    removeItemsFromCart(items.map { it.key })
                }
            }
        }

}

data class ProductsState(
    val data: List<Products>? = null,
    val page: Int = 0,
    val hasMore: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false,
    val success: Boolean = false
)

sealed class SortOrder(@StringRes val text: Int) {
    object Asc : SortOrder(R.string.asc)
    object Desc : SortOrder(R.string.desc)
}

enum class SortType {
    NAME, PRICE, CATEGORY, NONE
}