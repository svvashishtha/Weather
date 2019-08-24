package com.example.sample.utils

import android.content.Context
import javax.inject.Inject

class ResourceUtils @Inject constructor(context: Context) {

    private var mContext: Context = context


    fun getString(id: Int): String {
        return if (mContext.resources?.getString(id) != null)
            mContext.resources?.getString(id)!!
        else ""
    }

    fun getDimen(dimension: Int): Float? {

        return mContext.resources?.getDimension(dimension)

    }

    fun getColor(color: Int): Int {
        return mContext.resources.getColor(color)
    }

}