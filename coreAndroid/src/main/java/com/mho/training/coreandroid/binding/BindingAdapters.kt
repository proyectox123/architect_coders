package com.mho.training.coreandroid.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mho.training.coreandroid.R
import com.mho.training.coreandroid.extensions.loadUrl
import com.mho.training.coreandroid.extensions.loadUrlCircular

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url.isNullOrBlank()){
        setImageResource(R.drawable.ic_camera)
    }else{
        loadUrl(url, R.drawable.ic_camera, R.drawable.ic_broken_image)
    }
}

@BindingAdapter("circularPhoto")
fun ImageView.bindCircularPhotoUrl(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.ic_camera)
    } else {
        loadUrlCircular(url, R.drawable.ic_camera, R.drawable.ic_broken_image)
    }
}
