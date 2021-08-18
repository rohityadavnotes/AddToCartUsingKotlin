package com.add.to.cart.using.kotlin.baseadapter.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
public class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val views: SparseArray<View> = SparseArray()

    public fun <V : View?> getView(@IdRes viewId: Int): V? {
        var view = views[viewId]
        if (view == null) {
            view = getViewOrNull<View>(viewId)
            assert(view == null) { "view == null" }
            views.put(viewId, view)
        }
        return view as V?
    }

    public fun <V : View?> getViewOrNull(@IdRes viewId: Int): V? {
        var view = views[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as V?
    }

    public fun <V : View?> findView(@IdRes viewId: Int): V {
        return itemView.findViewById(viewId)
    }

    public fun setText(@IdRes viewId: Int, value: CharSequence?): BaseViewHolder {
        val textView = getViewOrNull<TextView>(viewId)!!
        textView.text = value
        return this
    }

    public fun setText(@IdRes viewId: Int, @StringRes resId: Int): BaseViewHolder {
        val textView = getViewOrNull<TextView>(viewId)!!
        textView.setText(resId)
        return this
    }

    public fun setTextColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val textView = getViewOrNull<TextView>(viewId)!!
        textView.setTextColor(color)
        return this
    }

    public fun setTextColorRes(@IdRes viewId: Int, @ColorRes resId: Int): BaseViewHolder {
        val textView = getViewOrNull<TextView>(viewId)!!
        textView.setTextColor(itemView.resources.getColor(resId))
        return this
    }

    public fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BaseViewHolder {
        val imageView = getViewOrNull<ImageView>(viewId)!!
        imageView.setImageResource(imageResId)
        return this
    }

    public fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): BaseViewHolder {
        val imageView = getViewOrNull<ImageView>(viewId)!!
        imageView.setImageDrawable(drawable)
        return this
    }

    public fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): BaseViewHolder {
        val imageView = getViewOrNull<ImageView>(viewId)!!
        imageView.setImageBitmap(bitmap)
        return this
    }

    public fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.setBackgroundColor(color)
        return this
    }

    public fun setBackgroundColorRes(@IdRes viewId: Int, @ColorRes resId: Int): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.setBackgroundColor(itemView.resources.getColor(resId))
        return this
    }

    public fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.setBackgroundResource(backgroundRes)
        return this
    }

    public fun setVisible(@IdRes viewId: Int, isVisible: Boolean): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    public fun setGone(@IdRes viewId: Int, isGone: Boolean): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.visibility = if (isGone) View.GONE else View.VISIBLE
        return this
    }

    public fun setEnabled(@IdRes viewId: Int, isEnabled: Boolean?): BaseViewHolder {
        val view = getViewOrNull<View>(viewId)!!
        view.isEnabled = isEnabled!!
        return this
    }
}