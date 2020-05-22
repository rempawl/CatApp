package com.example.catapp.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.catapp.data.Result

@BindingAdapter("showWhen")
fun showWhen(view: View, shouldShow: Boolean) {
    view.visibility = if (shouldShow) View.VISIBLE else View.GONE
}







