package com.section11.components.recycler.adapter

interface RecyclerViewHolderInterface {

    fun setTitle(title: String)

    fun showTitleLoading()

    fun setImage(imageUrl: String)

    fun showImageLoading()
}
