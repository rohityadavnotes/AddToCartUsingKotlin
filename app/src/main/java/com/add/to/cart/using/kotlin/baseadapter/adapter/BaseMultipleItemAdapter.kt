package com.add.to.cart.using.kotlin.baseadapter.adapter

import android.util.SparseIntArray
import android.view.ViewGroup
import androidx.annotation.LayoutRes

public abstract class BaseMultipleItemAdapter<T : MultiItemEntity?, VH : BaseViewHolder?> : BaseAdapter<T, VH> {

    /**
     * The mapping collection of itemType and its corresponding layout file;
     */
    private val itemTypeLayouts: SparseIntArray by lazy(LazyThreadSafetyMode.NONE) { SparseIntArray() }

    public constructor() {}

    public constructor(data: List<T>?) : super(data as MutableList<T>?) {}

    override fun getItemViewType(position: Int): Int {
        if (data != null) {
            val t: T? = data!![position]
            if (t != null) {
                return t.getItemType()
            }
        }
        return 0
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutResId = itemTypeLayouts[viewType]
        return createBaseViewHolder(parent, layoutResId)
    }

    /**
     * Add itemType and its corresponding layout file to this mapping set;
     *
     * @param viewType
     * @param layoutResId
     */
    protected fun addItemType(viewType: Int, @LayoutRes layoutResId: Int) {
        itemTypeLayouts.put(viewType, layoutResId)
    }
}