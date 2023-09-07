package com.simon.storeharmonytest.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.simon.storeharmonytest.data.entities.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Query("SELECT * FROM CART")
    fun getCart(): Flow<List<CartEntity>>


    @Query("DELETE FROM cart WHERE primaryKey IN (:primaryKey)")
    suspend fun deleteById(primaryKey: List<Int>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addToCart(vararg items: CartEntity)

    @Delete
    suspend fun delete(cart: CartEntity)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()


}

@Database(entities = [CartEntity::class], version = 2)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun notesDao(): ProductsDao
}