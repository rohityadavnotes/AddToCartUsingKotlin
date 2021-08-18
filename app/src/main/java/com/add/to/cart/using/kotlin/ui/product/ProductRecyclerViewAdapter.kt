package com.add.to.cart.using.kotlin.ui.product

import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.baseadapter.adapter.BaseSingleItemAdapter
import com.add.to.cart.using.kotlin.baseadapter.adapter.BaseViewHolder
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.utilities.BitmapManager
import java.text.NumberFormat
import java.util.*

class ProductRecyclerViewAdapter : BaseSingleItemAdapter<ProductWithCart?, BaseViewHolder?>() {

    init {
        addChildClickViewIds(R.id.addToCartButton)
    }

    override val viewHolderLayoutResId: Int get() = R.layout.product_item_row

    override fun convert(viewHolder: BaseViewHolder?, productWithCart: ProductWithCart?, position: Int) {
        viewHolder?.setImageBitmap(R.id.productCircleImageView, BitmapManager.byteToBitmap(productWithCart?.product?.productImage))
        viewHolder?.setText(R.id.productNameTextView, "Name : " + productWithCart?.product?.name)
        viewHolder?.setText(R.id.productPriceTextView, "Price : " + productWithCart?.product?.price?.let { price(it) })
    }

    fun price(total: Int): String {
        val locale = Locale("en", "IN")
        val fmt = NumberFormat.getCurrencyInstance(locale)
        return fmt.format(total.toLong())
    }
}