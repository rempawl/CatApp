package com.example.catapp.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isNetworkError" )
fun showWhenIsNetworkError(view: View, isNetworkError: Boolean) {
    if(isNetworkError) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("isLoading")
fun showWhenIsLoading(view: View, isLoading: Boolean){
    view.visibility = if(isLoading) View.VISIBLE else View.GONE
}
