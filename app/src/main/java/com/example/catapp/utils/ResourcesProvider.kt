package com.example.catapp.utils

import android.content.Context
import javax.inject.Inject

class ResourcesProvider @Inject constructor( private val context: Context) {

    fun getString(id: Int) = context.getString(id)
}