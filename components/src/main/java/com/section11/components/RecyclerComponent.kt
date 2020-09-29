package com.section11.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.recycler_component_layout.view.*

class RecyclerComponent : ConstraintLayout {

    init {
        LayoutInflater.from(context).inflate(R.layout.recycler_component_layout, this)
        recycler_title.text = "TITLE"
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr)
}