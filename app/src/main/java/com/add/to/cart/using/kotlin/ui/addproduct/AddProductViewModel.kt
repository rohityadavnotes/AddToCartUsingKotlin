package com.add.to.cart.using.kotlin.ui.addproduct

import android.content.Context
import androidx.lifecycle.ViewModel
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.data.repository.LocalRepository

class AddProductViewModel(context: Context) : ViewModel() {

    private val repository: LocalRepository = LocalRepository(context)

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }
}