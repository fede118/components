package com.section11.components.recycler.factory

import android.content.Context
import android.util.AttributeSet
import com.section11.components.R

private const val ZERO = 0

internal data class RecyclerComponentAttr(
    val title: String?,
    val placeHolderItems: Int
)

internal object RecyclerComponentAttrParser {
    fun parse (context: Context, attr: AttributeSet?) : RecyclerComponentAttr {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.RecyclerComponent)
        val title = typedArray.getString(R.styleable.RecyclerComponent_recyclerComponentTitle)
        val placeHolderItems = typedArray.getInt(R.styleable.RecyclerComponent_recyclerComponentPlaceHolderItems, ZERO)

        return RecyclerComponentAttr(title = title, placeHolderItems = placeHolderItems).also {
            typedArray.recycle()
        }
    }
}

