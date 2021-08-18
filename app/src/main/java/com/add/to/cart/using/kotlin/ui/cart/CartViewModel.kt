package com.add.to.cart.using.kotlin.ui.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.data.repository.LocalRepository
import java.util.*

class CartViewModel(context: Context) : ViewModel() {

    private val repository: LocalRepository = LocalRepository(context)
    private val listOfCartProduct: LiveData<List<Cart>>

    init {
        listOfCartProduct = repository.listOfCartProduct
    }

    fun insertCart(cart: Cart) {
        repository.insertCart(cart)
    }

    fun updateQuantity(quantity: Int, totalPrice: Int, updatedAt: Date, cartId: Int) {
        repository.updateQuantity(quantity, totalPrice, updatedAt, cartId)
    }

    val listOfProductWithCart: LiveData<List<ProductWithCart>>
        get() = repository.listOfProductWithCart

    fun deleteProductFromCart(cartId: Int) {
        repository.deleteProductFromCart(cartId)
    }
}