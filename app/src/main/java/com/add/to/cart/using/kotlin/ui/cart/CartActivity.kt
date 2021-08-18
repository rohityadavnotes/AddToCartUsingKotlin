package com.add.to.cart.using.kotlin.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemChildClick
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemClick
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.ui.base.BaseActivity
import com.add.to.cart.using.kotlin.utilities.LayoutManagerUtils.getLinearLayoutManagerVertical
import com.google.android.material.button.MaterialButton

class CartActivity : BaseActivity() {

    companion object {
        private val TAG: String = CartActivity::class.java.simpleName
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalHeadingTextView: TextView
    private lateinit var totalPriceTextView:TextView
    private lateinit var checkoutMaterialButton: MaterialButton

    private lateinit var arrayList: ArrayList<ProductWithCart>
    private lateinit var adapter: CartRecyclerViewAdapter

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartViewModelFactory: CartViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_cart
    }

    override fun initializeView() {
        recyclerView            = findViewById(R.id.recyclerView);
        totalHeadingTextView    = findViewById(R.id.totalHeadingTextView);
        totalPriceTextView      = findViewById(R.id.totalPriceTextView);
        checkoutMaterialButton  = findViewById(R.id.checkoutMaterialButton);
    }

    override fun initializeObject() {
        cartViewModelFactory = CartViewModelFactory(this)
        cartViewModel = ViewModelProvider(this, cartViewModelFactory).get(CartViewModel::class.java)

        arrayList = ArrayList()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = getLinearLayoutManagerVertical(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        val itemDecoration: ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        adapter = CartRecyclerViewAdapter()
        adapter.addArrayList(arrayList)
        recyclerView.adapter = adapter
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
        val productWithCartObserver: Observer<List<ProductWithCart>> = object : Observer<List<ProductWithCart>> {
            override fun onChanged(productWithCartList: List<ProductWithCart>) {
                if (productWithCartList.isNotEmpty()) {
                    arrayList.clear()

                    for (i in productWithCartList.indices)
                    {
                        Log.e(TAG, "===== ProductWithCart =====")
                        Log.e(TAG, "Product Name : " + productWithCartList[i].product.name)

                        if (productWithCartList[i].cart != null)
                        {
                            arrayList.add(productWithCartList[i])
                        }
                        else
                        {
                            Log.e(TAG, "===== Product Not Added To Cart =====")
                        }
                    }

                    totalPriceTextView.text = price(grandTotal(arrayList))
                    adapter.replaceArrayList(arrayList)
                }
            }
        }

        cartViewModel.listOfProductWithCart.observe(this /*Activity or Fragment*/, productWithCartObserver)
    }

    override fun addTextChangedListener() {
    }

    override fun setOnClickListener() {
        checkoutMaterialButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                fun onClick(v: View?) {
                }
            }
        })

        adapter.setOnRecyclerViewItemClick(object : OnRecyclerViewItemClick<ProductWithCart?> {
            override fun onItemClick(itemView: View, productWithCart: ProductWithCart?, position: Int) {
                //adapter.checkCheckBox(position, !(adapter.isSelected(position)));
            }
        })

        adapter.setOnRecyclerViewItemChildClick(object : OnRecyclerViewItemChildClick<ProductWithCart?> {
            override fun onItemChildClick(viewChild: View, t: ProductWithCart?, position: Int) {
                when (viewChild.getId()) {
                    R.id.quantityDecrementTextViewButton ->
                        if(t != null) {
                            if (t.cart != null)
                            {
                                if (t.cart.quantity > 1) {
                                    cartViewModel.updateQuantity(
                                        t.cart.quantity - 1,
                                        calculatePrice(t.product.price, t.cart.quantity - 1),
                                        getCurrentDate(),
                                        t.cart.cartId
                                    )
                                } else {
                                    cartViewModel.deleteProductFromCart(t.cart.cartId)
                                }
                            }
                        }
                    R.id.quantityIncrementTextViewButton ->
                    if(t != null) {
                        if (t.cart != null)
                        {
                            cartViewModel.updateQuantity(
                                t.cart.quantity + 1,
                                calculatePrice(
                                    t.product.price,
                                    t.cart.quantity + 1
                                ),
                                getCurrentDate(),
                                t.cart.cartId
                            )
                        }
                    }
                    R.id.checkBox -> adapter.checkCheckBox(position, !adapter.isSelected(position))
                    else -> {
                        print("none of the above")
                    }
                }
            }
        })
    }

    /* Called whenever we call invalidateOptionsMenu() */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /* Inflate the menu. it adds items to the action bar if it's present. */
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return when (id) {
            R.id.action_select_all -> {
                selectAll()
                true
            }
            R.id.action_delete_selected -> {
                deleteSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
    }

    fun selectAll() {
        for (i in arrayList.indices) adapter.checkCheckBox(i, true)
    }

    fun deselectAll() {
        adapter.deselectAll()
    }

    fun deleteSelected() {
        val selectedRows = adapter.getSelectedIds()
        if (selectedRows!!.size() > 0) {
            for (i in selectedRows.size() - 1 downTo 0) {
                if (selectedRows.valueAt(i)) {
                    arrayList[i].cart?.cartId?.let { cartViewModel.deleteProductFromCart(it) }
                    adapter.removeSingleItemUsingPosition(selectedRows.keyAt(i))
                }
            }
            adapter.deselectAll()
        }
    }

    fun getSelected() {
        val selectedRows = adapter.getSelectedIds()
        if (selectedRows!!.size() > 0)
        {
            var selectedRowLabel:String = ""

            for (i in 0 until selectedRows.size())
            {
                val key = selectedRows.keyAt(i)
                selectedRowLabel = "Selected Position $key \n Element at $key, is $selectedRows[$key]"
            }

            Toast.makeText(applicationContext, "Selected Rows\n$selectedRowLabel", Toast.LENGTH_SHORT).show()
        }
    }
}