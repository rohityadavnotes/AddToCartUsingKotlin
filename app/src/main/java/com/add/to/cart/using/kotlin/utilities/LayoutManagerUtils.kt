package com.add.to.cart.using.kotlin.utilities;

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object LayoutManagerUtils {

    fun getLinearLayoutManagerVertical(context: Context?): LinearLayoutManager {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        return linearLayoutManager
    }

    fun getLinearLayoutManagerHorizontal(context: Context?): LinearLayoutManager {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return linearLayoutManager
    }

    fun getGridLayoutManagerVertical(context: Context?): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        return gridLayoutManager
    }

    fun getGridLayoutManagerHorizontal(context: Context?): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return gridLayoutManager
    }

    val staggeredGridLayoutManagerVertical: StaggeredGridLayoutManager
        get() = StaggeredGridLayoutManager(
            3,
            StaggeredGridLayoutManager.VERTICAL
        )

    val staggeredGridLayoutManagerHorizontal: StaggeredGridLayoutManager
        get() = StaggeredGridLayoutManager(
            3,
            StaggeredGridLayoutManager.HORIZONTAL
        )
}