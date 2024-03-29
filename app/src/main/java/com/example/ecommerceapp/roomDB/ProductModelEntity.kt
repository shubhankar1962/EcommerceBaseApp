package com.example.ecommerceapp.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "products")
data class ProductModelEntity (

    @PrimaryKey
    @Nonnull
    val productId:String,

    @ColumnInfo(name = "productName")
    val productName:String?,

    @ColumnInfo(name = "productImage")
    val productImage:String?,
    @ColumnInfo(name = "productSp")
    val productSp:String?,

    )