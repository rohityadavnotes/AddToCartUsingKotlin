package com.add.to.cart.using.kotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart

@Dao
interface ProductDao {
    /*============================================ Insert ========================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product?): Long

    /*============================================= Read =========================================*/
    @get:Query("SELECT * FROM product")
    val listOfProduct: LiveData<List<Product>>

    /*==================================== One to One Relationship ===============================*/
    @get:Query("SELECT * FROM product")
    @get:Transaction
    val listOfProductWithCart: LiveData<List<ProductWithCart>>
}