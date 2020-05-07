package com.example.catapp.utils.providers

import android.content.Context
import javax.inject.Inject

class ResourcesProviderImpl @Inject constructor(context: Context) : ResourcesProvider(context) {
    override fun getString(id: Int) = context.getString(id)
}