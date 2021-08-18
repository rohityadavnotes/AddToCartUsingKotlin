package com.add.to.cart.using.kotlin.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.add.to.cart.using.kotlin.data.local.dao.CartDao
import com.add.to.cart.using.kotlin.data.local.dao.ProductDao
import com.add.to.cart.using.kotlin.data.local.database.MyRoomDatabase.Companion.getInstance
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import java.util.*

class LocalRepository(context: Context) {

    private val productDao: ProductDao
    private val cartDao: CartDao

    init {
        val myRoomDatabase = getInstance(context)
        productDao = myRoomDatabase!!.productDao
        cartDao = myRoomDatabase.cartDao
    }

    fun insertProduct(product: Product?) {
        Thread {
            val insert = productDao.insertProduct(product)
            Log.e("INSERT", "Insert Success : $insert")
        }.start()
    }

    val listOfProduct: LiveData<List<Product>> get() = productDao.listOfProduct

    val listOfProductWithCart: LiveData<List<ProductWithCart>> get() = productDao.listOfProductWithCart

    fun insertCart(cart: Cart?) {
        Thread {
            val insert = cartDao.insertCart(cart)
            Log.e("CART", "Insert Success : $insert")
        }.start()
    }

    fun updateQuantity(quantity: Int, totalPrice: Int, updatedAt: Date?, cartId: Int) {
        Thread {
            val insert = cartDao.updateQuantity(quantity, totalPrice, updatedAt, cartId).toLong()
            Log.e("CART", "Update Quantity Success : $insert")
        }.start()
    }

    val listOfCartProduct: LiveData<List<Cart>> get() = cartDao.listOfCartProduct

    val numberOfProductAddedIntoCart: LiveData<Int> get() = cartDao.numberOfProductAddedIntoCart

    fun deleteProductFromCart(cartId: Int) {
        Thread {
            val insert = cartDao.deleteProductFromCart(cartId).toLong()
            Log.e("CART", "Delete Success : $insert")
        }.start()
    }
}