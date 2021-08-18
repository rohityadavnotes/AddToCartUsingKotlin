package com.add.to.cart.using.kotlin.baseadapter.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class BaseSingleItemAdapter<T, VH : BaseViewHolder?> : BaseAdapter<T, VH> {

    constructor() {}

    constructor(data: List<T>?) : super(data as MutableList<T>?) {}

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createBaseViewHolder(parent, viewHolderLayoutResId)
    }

    /**
     * The layout id of the item
     * @return
     */
    @get:LayoutRes
    protected abstract val viewHolderLayoutResId: Int
}