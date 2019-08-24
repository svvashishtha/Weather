package com.example.sample.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.example.sample.R
import com.google.android.material.textview.MaterialTextView

class IconFontTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr) {

    init {
        var typeface: Typeface? = null
        try {
            typeface = ResourcesCompat.getFont(context, R.font.weathericons_regular_webfont)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setTypeface(typeface)
    }

}