package com.mho.training.coreandroid.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }

fun ImageView.loadUrl(url: String, placeholder: Int = 0, errorPlaceholder: Int = 0) {
    Glide.with(context)
        .load(url)
        .error(errorPlaceholder)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadUrlCircular(url: String, placeholder: Int = 0, errorPlaceholder: Int = 0) {
    Glide.with(context)
        .load(url)
        .error(errorPlaceholder)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)
}

fun String.generateDateFormat(inputPatter: String, outputPatter: String): String{
    val input = SimpleDateFormat(inputPatter, Locale.US)
    val output = SimpleDateFormat(outputPatter, Locale.US)
    try {
        input.parse(this)?.let {
            return output.format(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this.viewModelStore, vmFactory)[T::class.java]
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this.viewModelStore, vmFactory)[T::class.java]
}

fun FragmentActivity.addFragment(containerViewId: Int, fragment: Fragment){
    supportFragmentManager
        .beginTransaction()
        .add(containerViewId, fragment)
        .commit()
}

@Suppress("unused")
inline fun <reified T> T.logD(message: String){
    Log.d(T::class.java.simpleName, message)
}
