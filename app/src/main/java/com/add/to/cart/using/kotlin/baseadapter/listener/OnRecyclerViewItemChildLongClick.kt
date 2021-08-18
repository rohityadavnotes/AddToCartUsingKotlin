package com.add.to.cart.using.kotlin.baseadapter.listener

import android.view.View

public interface OnRecyclerViewItemChildLongClick<T> {
    fun onItemChildLongClick(viewChild: View, t: T, position: Int)
}