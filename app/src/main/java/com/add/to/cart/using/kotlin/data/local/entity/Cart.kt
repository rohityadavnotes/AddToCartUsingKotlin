package com.add.to.cart.using.kotlin.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "cart",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf("product_id"),
        childColumns = arrayOf("product_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cart constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "cart_id") var cartId : Int,
    @ColumnInfo(name = "product_id") var productId : Int,
    var quantity : Int,
    @ColumnInfo(name = "total_price") var totalPrice: Int,
    @ColumnInfo(name = "created_at") var createdAt : Date,
    @ColumnInfo(name = "updated_at") var updatedAt : Date
)