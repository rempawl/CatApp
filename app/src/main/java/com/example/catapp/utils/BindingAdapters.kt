package com.example.catapp.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("showWhen" )
fun showWhen(view: View, isNetworkError: Boolean) {
    view.visibility =if(isNetworkError) {
          View.VISIBLE
    }else
        View.GONE
}

@BindingAdapter("isLoading")
fun showWhenIsLoading(view: View, isLoading: Boolean){
    view.visibility = if(isLoading) View.VISIBLE else View.GONE
}
