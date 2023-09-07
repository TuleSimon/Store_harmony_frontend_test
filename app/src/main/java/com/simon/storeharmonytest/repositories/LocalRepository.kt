package com.simon.storeharmonytest.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.simon.storeharmonytest.data.database.ProductsDatabase
import com.simon.storeharmonytest.data.entities.CartEntity
import com.simon.storeharmonytest.data.models.UserProfile
import com.simon.storeharmonytest.data.models.toUserProfile
import com.simon.storeharmonytest.data.sources.datastore.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val utils: DataStoreUtils,
    private val productsDatabase: ProductsDatabase
) : _LocalRepository {

    private val dao = productsDatabase.notesDao()
    override suspend fun editUserProfile(user: UserProfile) {
        withContext(Dispatchers.IO) {
            utils.editProfile(user.toGsonString())
        }
    }

    override fun getUserProfile(): Flow<UserProfile?> {
        return utils.profile.map { it?.toUserProfile() }
    }

    override fun getCart(): Flow<List<CartEntity>> {
        return dao.getCart()
    }

    override suspend fun addToCart(vararg items: CartEntity) {
        withContext(Dispatchers.IO) {
            dao.addToCart(*items)
        }
    }

    override suspend fun removeFromCart(primaryKey: List<Int>) {
        withContext(Dispatchers.IO) {
            dao.deleteById(primaryKey)
        }
    }

    override suspend fun clearCart() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

}


interface _LocalRepository {
    suspend fun editUserProfile(user: UserProfile)
    fun getUserProfile(): Flow<UserProfile?>

    fun getCart(): Flow<List<CartEntity>>

    suspend fun addToCart(vararg items: CartEntity)

    suspend fun removeFromCart(primaryKey: List<Int>)

    suspend fun clearCart()
}