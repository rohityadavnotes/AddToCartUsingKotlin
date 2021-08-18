package com.add.to.cart.using.kotlin.ui.cart

import android.util.Log
import android.util.SparseBooleanArray
import android.widget.CheckBox
import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.baseadapter.adapter.BaseSingleItemAdapter
import com.add.to.cart.using.kotlin.baseadapter.adapter.BaseViewHolder
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.utilities.BitmapManager
import java.text.NumberFormat
import java.util.*

class CartRecyclerViewAdapter : BaseSingleItemAdapter<ProductWithCart?, BaseViewHolder?>() {

    private val TAG = CartRecyclerViewAdapter::class.java.simpleName
    private val selectedItemPosition: SparseBooleanArray

    init {
        addChildClickViewIds(R.id.quantityDecrementTextViewButton)
        addChildClickViewIds(R.id.quantityIncrementTextViewButton)
        addChildClickViewIds(R.id.checkBox)
        selectedItemPosition = SparseBooleanArray()
    }

    override val viewHolderLayoutResId: Int get() = R.layout.cart_product_item_row

    override fun convert(viewHolder: BaseViewHolder?, productWithCart: ProductWithCart?, position: Int) {
        viewHolder?.setImageBitmap(R.id.productCircleImageView, BitmapManager.byteToBitmap(productWithCart?.product?.productImage))
        viewHolder?.setText(R.id.productNameTextView, "Name : " + productWithCart?.product?.name)
        viewHolder?.setText(R.id.productPriceTextView, "Price : " + productWithCart?.product?.price?.let { price(it) })
        viewHolder?.setText(R.id.quantityTextView, productWithCart?.cart?.quantity.toString())

        if (isSelected(position)) {
            val checkBox: CheckBox = viewHolder!!.findView(R.id.checkBox)
            checkBox.setChecked(true)
        } else {
            val checkBox: CheckBox = viewHolder!!.findView(R.id.checkBox)
            checkBox.setChecked(false)
        }
    }

    fun price(total: Int): String {
        val locale = Locale("en", "IN")
        val fmt = NumberFormat.getCurrencyInstance(locale)
        return fmt.format(total.toLong())
    }

    /**
     * Select all checkbox
     */
    fun selectAll() {
        Log.d(TAG, "selectAll() : " + "arrayList")
    }

    /**
     * Remove all checkbox
     */
    fun deselectAll() {
        Log.d(TAG, "deselectAll() : " + "arrayList")
        val selection = getSelectedItems()
        selectedItemPosition.clear()
        for (i in selection) {
            notifyItemChanged(i)
        }
    }

    /**
     * Check the Checkbox if not checked, if already check then unchecked
     */
    fun checkCheckBox(position: Int, value: Boolean) {
        if (value) {
            selectedItemPosition.put(position, true)
        } else {
            selectedItemPosition.delete(position)
        }
        Log.e(TAG, "selectAll() : $selectedItemPosition")
        //notifyDataSetChanged();
        notifyItemChanged(position)
    }

    /**
     * Return the selected Checkbox position
     */
    fun getSelectedIds(): SparseBooleanArray? {
        return selectedItemPosition
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    fun getSelectedItemCount(): Int {
        return selectedItemPosition.size()
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    fun getSelectedItems(): List<Int> {
        println("=============" + selectedItemPosition.size())
        val items: MutableList<Int> = ArrayList(selectedItemPosition.size())
        for (i in 0 until selectedItemPosition.size()) {
            items.add(selectedItemPosition.keyAt(i))
        }
        return items
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    fun isSelected(position: Int): Boolean {
        return getSelectedItems().contains(position)
    }
}