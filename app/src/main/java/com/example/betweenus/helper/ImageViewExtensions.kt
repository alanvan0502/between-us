package com.example.betweenus.helper

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(imageUrl: String?, goneWhenNullUrl: Boolean = true, applyOptions: ((requestOptions: RequestOptions) -> Unit)? = null): ImageView {
    // Fix for Glide check error when the activity is destroyed
    if(isActivityDestroyed) {
        return this
    }

    if (imageUrl.isNullOrEmpty()) {
        this.visibility = if(goneWhenNullUrl) View.GONE else View.INVISIBLE
    } else {
        this.visibility = View.VISIBLE
        val requestBuilder = Glide.with(this).load(imageUrl).transition(DrawableTransitionOptions.withCrossFade())
        applyOptions?.let {
            val requestOptions = RequestOptions()
            applyOptions(requestOptions)
            requestBuilder.apply(requestOptions)
        }
        requestBuilder.into(this)
    }

    return this

}

fun ImageView.loadImage(@RawRes @DrawableRes resId: Int?, goneWhenNullUrl: Boolean = true, applyOptions: ((requestOptions: RequestOptions) -> Unit)? = null): ImageView {
    // Fix for Glide check error when the activity is destroyed
    if(isActivityDestroyed) {
        return this
    }

    if (resId == null) {
        this.visibility = if(goneWhenNullUrl) View.GONE else View.INVISIBLE
    } else {
        this.visibility = View.VISIBLE
        val requestBuilder = Glide.with(this).load(resId).transition(DrawableTransitionOptions.withCrossFade())
        applyOptions?.let {
            val requestOptions = RequestOptions()
            applyOptions(requestOptions)
            requestBuilder.apply(requestOptions)
        }
        requestBuilder.into(this)
    }

    return this

}