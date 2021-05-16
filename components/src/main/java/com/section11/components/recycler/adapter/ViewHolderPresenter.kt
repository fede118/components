package com.section11.components.recycler.adapter

import com.section11.components.recycler.model.ViewHolderModel

class ViewHolderPresenter(private val view: RecyclerViewHolderInterface) {

    fun onBind(viewHolderModel: ViewHolderModel) {
        view.setTitle(viewHolderModel.getTitle())
        view.setImage(viewHolderModel.getImageUrl())
    }

}
