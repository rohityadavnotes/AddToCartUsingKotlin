package com.add.to.cart.using.kotlin.ui.product

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemChildClick
import com.add.to.cart.using.kotlin.baseadapter.listener.OnRecyclerViewItemClick
import com.add.to.cart.using.kotlin.customview.MyBadgeDrawable
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.data.local.relations.ProductWithCart
import com.add.to.cart.using.kotlin.ui.ProductDetailActivity
import com.add.to.cart.using.kotlin.ui.addproduct.AddProductActivity
import com.add.to.cart.using.kotlin.ui.base.BaseActivity
import com.add.to.cart.using.kotlin.ui.cart.CartActivity
import com.add.to.cart.using.kotlin.utilities.LayoutManagerUtils.getLinearLayoutManagerVertical

class ProductActivity : BaseActivity() {

    private val TAG = ProductActivity::class.java.simpleName

    private lateinit var menu: Menu
    private var badgeCount = 0

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productViewModelFactory: ProductViewModelFactory

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<ProductWithCart>
    private lateinit var adapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_product
    }

    override fun initializeView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    override fun initializeObject() {
        productViewModelFactory = ProductViewModelFactory(this)
        productViewModel = ViewModelProvider(this, productViewModelFactory).get(ProductViewModel::class.java)

        arrayList = ArrayList()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = getLinearLayoutManagerVertical(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        adapter = ProductRecyclerViewAdapter()
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
                    arrayList.addAll(productWithCartList)
                    adapter.replaceArrayList(arrayList)
                }
            }
        }
        productViewModel.listOfProductWithCart.observe(this /*Activity or Fragment*/, productWithCartObserver)

        val integerObserver: Observer<Int> = object : Observer<Int> {
            override fun onChanged(@Nullable integer: Int) {
                    badgeCount = integer
                    updateProductBadge(badgeCount)
            }
        }
        productViewModel.numberOfProductAddedIntoCart.observe(this /*Activity or Fragment*/, integerObserver)
    }

    override fun addTextChangedListener() {
    }

    override fun setOnClickListener() {
        adapter.setOnRecyclerViewItemClick(object : OnRecyclerViewItemClick<ProductWithCart?> {
            override fun onItemClick(itemView: View, productWithCart: ProductWithCart?, position: Int) {
                if (productWithCart != null) {
                    openActivityWithParcelable(productWithCart.product)
                }
            }
        })

        adapter.setOnRecyclerViewItemChildClick(object : OnRecyclerViewItemChildClick<ProductWithCart?> {
            override fun onItemChildClick(viewChild: View, productWithCart: ProductWithCart?, position: Int) {
                when (viewChild.getId()) {
                    R.id.addToCartButton ->
                        if (productWithCart != null)
                        {
                            Log.e(TAG, "===== ProductWithCart =====")
                            Log.e(TAG, "Product Name : " + productWithCart.product.name)
                            if (productWithCart.cart != null)
                            {
                                Log.e(TAG, "===== Already Added To Cart, Now Increase Quantity =====")
                                productViewModel.updateQuantity(
                                    productWithCart.cart.quantity + 1,
                                    calculatePrice(productWithCart.product.price, productWithCart.cart.quantity + 1),
                                    getCurrentDate(),
                                    productWithCart.cart.cartId
                                )
                            }
                            else
                            {
                                Log.e(TAG, "===== Product Not Added To Cart, Now Add =====")
                                val cart = Cart(
                                    (0..1000).random(),
                                    productWithCart.product.productId,
                                1,
                                    calculatePrice(productWithCart.product.price, 1),
                                    getCurrentDate(),
                                    getCurrentDate()
                                )
                                productViewModel.insertCart(cart)
                            }
                        }
                    else -> {
                        print("none of the above")
                    }
                }
            }
        })
    }

    private fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {
        val badge: MyBadgeDrawable
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        badge = if (reuse is MyBadgeDrawable) { reuse } else { MyBadgeDrawable(context) }
        badge.setCount(count.toString())
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }

    private fun updateProductBadge(count: Int) {
        badgeCount = count
        invalidateOptionsMenu()
    }

    /* Called whenever we call invalidateOptionsMenu() */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* Inflate the menu. it adds items to the action bar if it's present. */
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)

        /* Add to Cart Count */
        val menuItem: MenuItem = menu.findItem(R.id.action_view_cart)
        val icon = menuItem.getIcon() as LayerDrawable
        setBadgeCount(this, icon, badgeCount)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return when (id) {
            R.id.action_add_product -> {
                val addProductActivity =
                    Intent(this@ProductActivity, AddProductActivity::class.java)
                startActivity(addProductActivity)
                true
            }
            R.id.action_view_cart -> {
                val cartActivity = Intent(this@ProductActivity, CartActivity::class.java)
                startActivity(cartActivity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
    }

    private fun openActivityWithParcelable(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("parcelable_product_key", product)
        startActivity(intent)
    }
}