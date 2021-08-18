package com.add.to.cart.using.kotlin.ui

import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.ui.base.BaseActivity
import com.add.to.cart.using.kotlin.utilities.BitmapManager

class ProductDetailActivity : BaseActivity() {

    companion object {
        private val TAG: String = ProductDetailActivity::class.java.simpleName
    }

    lateinit var productImageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var priceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_product_detail
    }

    override fun initializeView() {
        productImageView    = findViewById(R.id.imageView)
        nameTextView        = findViewById(R.id.nameTextView)
        priceTextView       = findViewById(R.id.priceTextView)
    }

    override fun initializeObject() {
        displayParcelable()
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
    }

    override fun addTextChangedListener() {
    }

    override fun setOnClickListener() {
    }

    private fun displayParcelable() {
        if (intent != null && intent.hasExtra("parcelable_product_key")) {
            val productParcelable = intent.getParcelableExtra<Parcelable>("parcelable_product_key") as Product?
            productImageView.setImageBitmap(BitmapManager.byteToBitmap(productParcelable?.productImage))
            nameTextView.text = productParcelable?.name
            priceTextView.text = productParcelable?.price.toString()
        }
    }
}