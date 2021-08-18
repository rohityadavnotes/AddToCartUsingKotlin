package com.add.to.cart.using.kotlin.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.entity.Product

data class ProductWithCart (
    @Embedded val product : Product,
    @Relation(parentColumn = "product_id", entityColumn = "product_id") val cart : Cart?
)