package com.add.to.cart.using.kotlin.baseadapter.listener

import android.view.View

public interface OnRecyclerViewItemChildClick<T> {
    fun onItemChildClick(viewChild: View, t: T, position: Int)
}