package com.add.to.cart.using.kotlin.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.add.to.cart.using.kotlin.data.local.converter.DateConverter
import com.add.to.cart.using.kotlin.data.local.dao.CartDao
import com.add.to.cart.using.kotlin.data.local.dao.ProductDao
import com.add.to.cart.using.kotlin.data.local.entity.Cart
import com.add.to.cart.using.kotlin.data.local.entity.Product

@Database(entities = [Product::class, Cart::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class MyRoomDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getInstance(context: Context): MyRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, MyRoomDatabase::class.java, "Product.db").build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract val productDao: ProductDao
    abstract val cartDao: CartDao
}