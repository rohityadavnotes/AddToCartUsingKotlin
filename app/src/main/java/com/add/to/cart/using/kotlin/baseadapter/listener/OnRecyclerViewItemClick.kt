package com.add.to.cart.using.kotlin.baseadapter.listener

import android.view.View

public interface OnRecyclerViewItemClick<T> {
    fun onItemClick(itemView: View, t: T, position: Int)
}