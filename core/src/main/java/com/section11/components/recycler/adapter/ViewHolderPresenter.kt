package com.section11.components.recycler.adapter

import com.section11.components.recycler.model.ViewHolderModel

class ViewHolderPresenter(private val view: RecyclerViewHolderInterface) {

    fun onBind(viewHolderModel: ViewHolderModel) {
        viewHolderModel.getTitle()?.let {
            view.setTitle(it)
        } ?: view.showTitleLoading()

        viewHolderModel.getImageUrl()?.let {
            view.setImage(it)
        } ?: view.showImageLoading()
    }
}
