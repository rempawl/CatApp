package com.example.catapp.utils.providers

import android.content.Context


abstract class ResourcesProvider constructor(protected val context: Context) {
    abstract fun getString(id: Int): String
}

