package com.example.ecommerceapp.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product:ProductModelEntity)

    @Delete
    suspend fun deleteProduct(product: ProductModelEntity)

    @Query("select * from products")
    fun getAllProducts():LiveData<List<ProductModelEntity>>

    @Query("select * from products where productId = :id")
    fun isExist(id:String):ProductModelEntity
}