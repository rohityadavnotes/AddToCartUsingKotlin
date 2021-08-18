package com.add.to.cart.using.kotlin.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") var productId : Int,
    @ColumnInfo(name = "product_image", typeAffinity = ColumnInfo.BLOB) var productImage: ByteArray,
    var name: String,
    var price: Int
) : Parcelable