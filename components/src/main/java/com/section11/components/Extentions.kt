package com.section11.components

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


fun View.animateIn() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.recycler_item_animate_in)
    this.startAnimation(animation)
}

fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode

//        override fun calculateDyToMakeVisible(view: View, snapPreference: Int): Int {
//            return super.calculateDyToMakeVisible(view, snapPreference) - 100.dp
//        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return 0.5f //pass as per your requirement
        }
    }

    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()