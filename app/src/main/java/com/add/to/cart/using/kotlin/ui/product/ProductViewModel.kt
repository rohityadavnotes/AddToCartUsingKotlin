package com.add.to.cart.using.kotlin.ui.product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.data.repository.LocalRepository
import java.util.*

class ProductViewModel(context: Context) : ViewModel() {

    private val repository: LocalRepository = LocalRepository(context)
    private val listOfProduct: LiveData<List<Product>>

    init {
        listOfProduct = repository.listOfProduct
    }

    fun insertProduct(product: Product?) {
        repository.insertProduct(product)
    }

    val listOfProductWithCart: LiveData<List<ProductWithCart>> get() = repository.listOfProductWithCart

    fun insertCart(cart: Cart?) {
        repository.insertCart(cart)
    }

    fun updateQuantity(quantity: Int, totalPrice: Int, updatedAt: Date?, cartId: Int) {
        repository.updateQuantity(quantity, totalPrice, updatedAt, cartId)
    }

    val numberOfProductAddedIntoCart: LiveData<Int> get() = repository.numberOfProductAddedIntoCart
}