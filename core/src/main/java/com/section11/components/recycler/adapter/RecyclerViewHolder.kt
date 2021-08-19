package com.section11.components.recycler.adapter

import android.graphics.drawable.Animatable
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.section11.components.R
import com.section11.components.animateIn
import com.section11.components.databinding.ViewHolderLayoutBinding
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel


class RecyclerViewHolder internal constructor(private val viewBinding: ViewHolderLayoutBinding)
    : RecyclerView.ViewHolder(viewBinding.root), RecyclerViewHolderInterface {

    private val presenter = ViewHolderPresenter(this)

    fun bind(viewHolderModel: ViewHolderModel, listener: RecyclerItemClickListener?) {
        viewBinding.root.setOnClickListener {
            listener?.onRecyclerItemClicked(viewHolderModel)
        }

        presenter.onBind(viewHolderModel)
    }

    override fun setTitle(title: String) {
        viewBinding.titleShimmerContainer.hideShimmer()
        viewBinding.titleShimmerContainer.stopShimmer()
        viewBinding.titleShimmerContainer.background = null

        viewBinding.itemTitle.text = title
        viewBinding.itemTitle.animateIn()

    }

    override fun setImage(imageUrl: String) {
        val controllerBuilder = Fresco.newDraweeControllerBuilder()
        controllerBuilder.setUri(imageUrl)
        controllerBuilder.oldController = viewBinding.itemImage.controller
        controllerBuilder.controllerListener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                if (viewBinding.imageShimmerContainer.isShimmerVisible) {
                    viewBinding.imageShimmerContainer.hideShimmer()
                    viewBinding.imageShimmerContainer.stopShimmer()

                    viewBinding.itemImage.animateIn()
                }
            }
        }
        viewBinding.itemImage.controller = controllerBuilder.build()
    }

    override fun showTitleLoading() {
        viewBinding.titleShimmerContainer.background ?: run {
            viewBinding.titleShimmerContainer.background =
                ContextCompat.getDrawable(viewBinding.root.context, R.drawable.shimmer_image_place_holder)
        }

        viewBinding.itemTitle.text = null
        viewBinding.titleShimmerContainer.showShimmer(true)
        viewBinding.titleShimmerContainer.startShimmer()
    }

    override fun showImageLoading() {
        removeCurrentImage()
        viewBinding.imageShimmerContainer.showShimmer(true)
        viewBinding.imageShimmerContainer.startShimmer()
    }

    private fun removeCurrentImage() {
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
            .path(java.lang.String.valueOf(R.drawable.blank_placeholder))
            .build()

        viewBinding.itemImage.setImageURI(uri.toString())
    }
}