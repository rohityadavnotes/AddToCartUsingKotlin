package com.add.to.cart.using.kotlin.baseadapter.listener

import android.view.View

public interface OnRecyclerViewItemLongClick<T> {
    fun onItemLongClick(itemView: View, t: T, position: Int)
}