package com.add.to.cart.using.kotlin.ui.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import java.text.NumberFormat
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }
    /*
     ***********************************************************************************************
     ********************************* BaseActivity abstract methods *******************************
     ***********************************************************************************************
     */
    @LayoutRes
    protected abstract fun getLayoutID(): Int
    protected abstract fun initializeView()
    protected abstract fun initializeObject()
    protected abstract fun initializeToolBar()
    protected abstract fun initializeCallbackListener()
    protected abstract fun addTextChangedListener()
    protected abstract fun setOnClickListener()
    /*
     ***********************************************************************************************
     ********************************* Activity lifecycle methods **********************************
     ***********************************************************************************************
     */
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate(Bundle savedInstanceState)")

        setContentView(getLayoutID())

        initializeView()
        initializeObject()
        initializeToolBar()
        initializeCallbackListener()
        addTextChangedListener()
        setOnClickListener()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    @CallSuper
    override fun onRestart() { /* Only called after onStop() */
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.i(TAG, "onBackPressed()")
    }

    open fun getCurrentDate(): Date {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.getTime()
    }

    open fun price(total: Int): String {
        val locale = Locale("en", "IN")
        val fmt: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        return fmt.format(total)
    }

    open fun calculatePrice(price: Int, quantity: Int): Int {
        return price * quantity
    }

    open fun grandTotal(productWithCarts: List<ProductWithCart>): Int {
        var totalPrice = 0
        for (i in productWithCarts.indices) {
            totalPrice += productWithCarts[i].cart?.totalPrice ?: 0
        }
        return totalPrice
    }
}