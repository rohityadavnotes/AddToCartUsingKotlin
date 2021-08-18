package com.add.to.cart.using.kotlin.ui.addproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.add.to.cart.using.kotlin.R
import com.add.to.cart.using.kotlin.customview.CircleImageView
import com.add.to.cart.using.kotlin.data.local.entity.Product
import com.add.to.cart.using.kotlin.ui.base.BaseActivity
import com.add.to.cart.using.kotlin.utilities.BitmapManager
import com.add.to.cart.using.kotlin.utilities.StringUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddProductActivity : BaseActivity() {

    private val TAG = AddProductActivity::class.java.simpleName

    private val PICK_IMAGE_REQUEST_CODE_ADD = 100

    private lateinit var addProductHeadingTextView: TextView

    private lateinit var productCircleImageView: CircleImageView
    private lateinit var selectProductImageFloatingActionButton: FloatingActionButton

    private lateinit var productNameTextInputLayout: TextInputLayout
    private lateinit var productPriceTextInputLayout:TextInputLayout
    private lateinit var productNameTextInputEditText: TextInputEditText
    private lateinit var productPriceTextInputEditText:TextInputEditText

    private lateinit var addMaterialButton: MaterialButton

    private lateinit var productImageByteArray: ByteArray
    private lateinit var productNameString: String
    private lateinit var productPriceString: String

    private lateinit var addProductViewModel: AddProductViewModel
    private lateinit var addProductViewModelFactory: AddProductViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_add_product
    }

    override fun initializeView() {
        addProductHeadingTextView = findViewById(R.id.addProductHeadingTextView);
        addProductHeadingTextView.setText("Add Product");

        productCircleImageView = findViewById(R.id.productCircleImageView);
        selectProductImageFloatingActionButton = findViewById(R.id.selectProductImageFloatingActionButton);

        productNameTextInputLayout = findViewById(R.id.productNameTextInputLayout);
        productNameTextInputEditText = findViewById(R.id.productNameTextInputEditText);
        productPriceTextInputLayout = findViewById(R.id.productPriceTextInputLayout);
        productPriceTextInputEditText = findViewById(R.id.productPriceTextInputEditText);

        addMaterialButton = findViewById(R.id.addMaterialButton);
    }

    override fun initializeObject() {
        addProductViewModelFactory = AddProductViewModelFactory(this)
        addProductViewModel = ViewModelProvider(this, addProductViewModelFactory).get(
            AddProductViewModel::class.java
        )
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
    }

    override fun addTextChangedListener() {
        productNameTextInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                if (text.length < 1) {
                    productNameTextInputLayout.isErrorEnabled = true
                    productNameTextInputLayout.error = "Please enter product name !"
                } else if (text.length > 0) {
                    productNameTextInputLayout.error = null
                    productNameTextInputLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        productPriceTextInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                if (text.length < 1) {
                    productPriceTextInputLayout.isErrorEnabled = true
                    productPriceTextInputLayout.error = "Please enter product price !"
                } else if (text.length > 0) {
                    productPriceTextInputLayout.error = null
                    productPriceTextInputLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun setOnClickListener() {
        selectProductImageFloatingActionButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showFileChooser(PICK_IMAGE_REQUEST_CODE_ADD)
            }
        })

        addMaterialButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                productNameString = productNameTextInputEditText.text.toString()
                productPriceString = productPriceTextInputEditText.text.toString()
                if (validation(productNameString, productPriceString) == null)
                {
                    val product = Product((0..1000).random(), productImageByteArray, productNameString, productPriceString.toInt())
                    addProductViewModel.insertProduct(product)
                    Toast.makeText(applicationContext, "Product Add Success", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(applicationContext, validation(productNameString, productPriceString), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE_ADD) {
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    val studentImageBitmap = BitmapManager.decodeUri(this@AddProductActivity, selectedImageUri, 400)
                    productImageByteArray = BitmapManager.bitmapToByte(studentImageBitmap)
                    productCircleImageView.setImageBitmap(
                        BitmapManager.byteToBitmap(
                            productImageByteArray
                        )
                    )
                }
            }
        }
    }

    private fun showFileChooser(SELECT_REQUEST_CODE: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_REQUEST_CODE)
    }

    private fun validation(firstName: String, lastName: String): String? {
        if (productImageByteArray == null) {
            return "Please select image !"
        } else if (StringUtils.isBlank(firstName)) {
            return "Please enter product name !"
        } else if (StringUtils.isBlank(lastName)) {
            return "Please enter product price !"
        }
        return null
    }
}