package com.section11.components.recycler.factory

import android.content.Context
import android.util.AttributeSet
import com.section11.components.R

internal data class RecyclerComponentAttr(
    val title: String?
)

internal object RecyclerComponentAttrParser {
    fun parse (context: Context, attr: AttributeSet?) : RecyclerComponentAttr {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.RecyclerComponent)
        val title = typedArray.getString(R.styleable.RecyclerComponent_recyclerComponentTitle)

        return RecyclerComponentAttr(title = title).also {
            typedArray.recycle()
        }
    }
}

