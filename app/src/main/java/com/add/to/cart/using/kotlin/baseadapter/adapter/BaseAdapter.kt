package com.add.to.cart.using.kotlin.baseadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemChildClick
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemChildLongClick
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemClick
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemLongClick
import java.util.*

public abstract class BaseAdapter<T, VH : BaseViewHolder?> : RecyclerView.Adapter<VH> {

    protected var data: MutableList<T>? = ArrayList()

    /*
     ***********************************************************************************************
     ******************************************* Constructor ***************************************
     ***********************************************************************************************
     */
    public constructor() {}

    public constructor(dataList: MutableList<T>?) {
        if (dataList != null) {
            data = dataList
        }
    }

    /*
     ***********************************************************************************************
     ************************************** CRUD Operation Methods *********************************
     ***********************************************************************************************
     */
    public fun addSingleItem(item: T?) {
        if (item != null) {
            data!!.add(item)
            notifyItemInserted(data!!.size - 1)
        }
    }

    public fun addSingleItemAtSpecificPosition(item: T?, position: Int) {
        if (item != null) {
            data!!.add(position, item)
            notifyItemInserted(position)
        }
    }

    public fun addArrayList(items: List<T>?) {
        if (items != null) {
            data!!.addAll(items)
            notifyDataSetChanged()
        }
    }

    public fun replaceArrayList(items: List<T>?) {
        if (items != null) {
            data!!.clear()
            addArrayList(items)
        }
    }

    public fun addArray(items: Array<T>?) {
        if (items != null) {
            addArrayList(Arrays.asList(*items))
            notifyDataSetChanged()
        }
    }

    public fun addOrUpdateSingleItem(item: T?) {
        if (item != null) {
            val i = data!!.indexOf(item)
            if (i >= 0) {
                data!![i] = item
                notifyItemChanged(i)
            } else {
                addSingleItem(item)
            }
        }
    }

    public fun addOrUpdateArrayList(items: List<T>?) {
        if (items != null) {
            for (item in items) {
                addOrUpdateSingleItem(item)
            }
            notifyDataSetChanged()
        }
    }

    public fun getSingleItemUsingPosition(position: Int): T {
        return data!![position]
    }

    public fun removeSingleItemUsingPosition(position: Int) {
        if (position >= 0 && position < data!!.size) {
            data!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    public fun removeSingleItem(item: T?) {
        if (item != null) {
            val position = data!!.indexOf(item)
            removeSingleItemUsingPosition(position)
        }
    }

    public fun clearAllItem() {
        data!!.clear()
        notifyDataSetChanged()
    }

    private fun getDataByPosition(position: Int): T? {
        var t: T? = null
        if (position != RecyclerView.NO_POSITION) {
            t = data!![position]
        }
        return t
    }

    /*
     ***********************************************************************************************
     ******************* Implemented methods form RecyclerView.ViewHolder class ********************
     ***********************************************************************************************
     */
    override fun getItemCount(): Int {
        return if (data != null) {
            data!!.size
        } else 0
    }

    protected fun createBaseViewHolder(parent: ViewGroup, @LayoutRes layoutResId: Int): VH {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        ) as VH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = onCreateDefViewHolder(parent, viewType)
        bindViewClickListener(viewHolder, viewType)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val t = data!![position]
        convert(viewHolder, t, position)
    }
    /*
     ***********************************************************************************************
     ****************************************** Click Events ***************************************
     ***********************************************************************************************
     */
    /**
     * Click event of row
     * Example : R.layout.recycler_view_item_row_a
     */
    protected var mOnRecyclerViewItemClick: OnRecyclerViewItemClick<T>? = null

    /**
     * Long press event of row
     * Example : R.layout.recycler_view_item_row_a
     */
    protected var mOnRecyclerViewItemLongClick: OnRecyclerViewItemLongClick<T>? = null

    /**
     * The click event of view
     * Example : foodImageView, foodNameTextView, foodDescriptionTextView
     */
    protected var mOnRecyclerViewItemChildClick: OnRecyclerViewItemChildClick<T>? = null

    /**
     * The long press event of view
     * Example : foodImageView, foodNameTextView, foodDescriptionTextView
     */
    protected var mOnRecyclerViewItemChildLongClick: OnRecyclerViewItemChildLongClick<T>? = null

    /**
     * Used to save the ViewId of the click event that needs to be set
     */
    protected var childClickViewIds: LinkedHashSet<Int>? = null

    /**
     * Used to save the ViewId that needs to be set for the long press click event
     */
    protected var childLongClickViewIds: LinkedHashSet<Int>? = null

    fun setOnRecyclerViewItemClick(onRecyclerViewItemClick: OnRecyclerViewItemClick<T>) {
        mOnRecyclerViewItemClick = onRecyclerViewItemClick
    }

    fun setOnRecyclerViewItemLongClick(onRecyclerViewItemLongClick: OnRecyclerViewItemLongClick<T>) {
        mOnRecyclerViewItemLongClick = onRecyclerViewItemLongClick
    }

    fun setOnRecyclerViewItemChildClick(onRecyclerViewItemChildClick: OnRecyclerViewItemChildClick<T>) {
        mOnRecyclerViewItemChildClick = onRecyclerViewItemChildClick
    }

    fun setOnRecyclerViewItemChildLongClick(onRecyclerViewItemChildLongClick: OnRecyclerViewItemChildLongClick<T>) {
        mOnRecyclerViewItemChildLongClick = onRecyclerViewItemChildLongClick
    }

    /**
     * Binding monitoring events
     *
     * @param viewHolder
     * @param viewType
     */
    private fun bindViewClickListener(viewHolder: VH, viewType: Int) {
        bindViewItemClickListener(viewHolder)
        bindViewItemChildClickListener(viewHolder)
    }

    private fun bindViewItemChildClickListener(viewHolder: VH) {
        if (mOnRecyclerViewItemChildClick != null && childClickViewIds != null) {
            for (viewId in childClickViewIds!!) {
                val viewChild = viewHolder!!.getViewOrNull<View>(viewId)
                viewChild?.setOnClickListener { v: View ->
                    val position = viewHolder.adapterPosition
                    val t = getDataByPosition(position)
                    t?.let { mOnRecyclerViewItemChildClick!!.onItemChildClick(v, it, position) }
                }
            }
        }
        if (mOnRecyclerViewItemChildLongClick != null && childLongClickViewIds != null) {
            for (viewId in childLongClickViewIds!!) {
                val viewChild = viewHolder!!.getViewOrNull<View>(viewId)
                viewChild?.setOnLongClickListener { v: View ->
                    val position = viewHolder.adapterPosition
                    val t = getDataByPosition(position)
                    t?.let {
                        mOnRecyclerViewItemChildLongClick!!.onItemChildLongClick(v, it, position)
                    }
                    true
                }
            }
        }
    }

    private fun bindViewItemClickListener(viewHolder: VH) {
        if (mOnRecyclerViewItemClick != null) {
            viewHolder!!.itemView.setOnClickListener { v: View ->
                val position = viewHolder.adapterPosition
                val t = getDataByPosition(position)
                t?.let { mOnRecyclerViewItemClick!!.onItemClick(v, it, position) }
            }
        }
        if (mOnRecyclerViewItemLongClick != null) {
            viewHolder!!.itemView.setOnLongClickListener { v: View ->
                val position = viewHolder.adapterPosition
                val t = getDataByPosition(position)
                t?.let { mOnRecyclerViewItemLongClick!!.onItemLongClick(v, it, position) }
                true
            }
        }
    }

    /**
     * Set the child view that needs to click event
     *
     * @param viewIds intArray
     */
    protected fun addChildClickViewIds(@IdRes vararg viewIds: Int) {
        if (childClickViewIds == null) {
            childClickViewIds = LinkedHashSet()
        }
        for (viewId in viewIds) {
            childClickViewIds!!.add(viewId)
        }
    }

    /**
     * Set the child view that needs to press and hold the click event
     *
     * @param viewIds intArray
     */
    protected fun addChildLongClickViewIds(@IdRes vararg viewIds: Int) {
        if (childLongClickViewIds == null) {
            childLongClickViewIds = LinkedHashSet()
        }
        for (viewId in viewIds) {
            childLongClickViewIds!!.add(viewId)
        }
    }

    /*
     ***********************************************************************************************
     ****************************************** Abstract methods ***********************************
     ***********************************************************************************************
     */
    protected abstract fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH
    protected abstract fun convert(viewHolder: VH, t: T, position: Int)
}