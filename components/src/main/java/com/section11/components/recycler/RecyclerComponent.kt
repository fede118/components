package com.section11.components.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.section11.components.databinding.RecyclerComponentLayoutBinding
import com.section11.components.recycler.adapter.RecyclerAdapter
import com.section11.components.recycler.factory.RecyclerComponentAttr
import com.section11.components.recycler.factory.RecyclerComponentAttrParser
import com.section11.components.recycler.factory.RecyclerComponentConfiguration
import com.section11.components.recycler.factory.RecyclerComponentConfigurationFactory
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel

class RecyclerComponent : ConstraintLayout {

    private lateinit var binding: RecyclerComponentLayoutBinding
    private lateinit var attrs: RecyclerComponentAttr
    private lateinit var adapter: RecyclerAdapter
    private var dataList: List<ViewHolderModel>? = null
    private var listener: RecyclerItemClickListener? = null

    constructor(
        context: Context,
        recyclerTitle: String,
        placeHolderItems: Int,
        modelList: List<ViewHolderModel>,
        listener: RecyclerItemClickListener
    ) : super(context) {
        initAttrs(recyclerTitle, placeHolderItems)
        this.dataList = modelList
        this.listener = listener
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : super(context, attributeSet) {
        attrs = RecyclerComponentAttrParser.parse(context, attributeSet)
        initAttrs(attrs.title, attrs.placeHolderItems)
    }

    var title: String?
        get() = attrs.title
        set(value) {
            attrs = attrs.copy(title = value)
            setRecyclerTitle(createConfiguration())
        }

    var placeHolderItems: Int
        get() = attrs.placeHolderItems
        set(value) {
            attrs = attrs.copy(placeHolderItems = value)
            setPlaceHolderItems(createConfiguration())
        }

    private fun initAttrs(title: String?, placeHolderItems: Int) {
        attrs = RecyclerComponentAttr(title = title, placeHolderItems = placeHolderItems)
        setupComponents(createConfiguration())
    }

    private fun createConfiguration() = RecyclerComponentConfigurationFactory.create(attrs)

    private fun setupComponents(config: RecyclerComponentConfiguration) {
        Fresco.initialize(context)

        binding = RecyclerComponentLayoutBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        initializeRecycler()

        setRecyclerTitle(config)
        setPlaceHolderItems(config)
    }

    private fun setRecyclerTitle(config: RecyclerComponentConfiguration) {
        if (config.title == null) {
            binding.recyclerTitle.visibility = View.GONE
            return
        }
        binding.recyclerTitle.visibility = View.VISIBLE
        binding.recyclerTitle.text = config.title
    }

    private fun setPlaceHolderItems(config: RecyclerComponentConfiguration) {
        if (config.placeHolderItems > ZERO) {
            val placeHolderItems = List(config.placeHolderItems) {
                object : ViewHolderModel {
                    override fun getId(): String? {
                        return null
                    }

                    override fun getTitle(): String? {
                        return null
                    }

                    override fun getImageUrl(): String? {
                        return null
                    }
                }
            }

            adapter.updateItemsList(placeHolderItems)
        } else {
            adapter.updateItemsList(emptyList())
        }

//        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
//            override fun getVerticalSnapPreference(): Int {
//                return SNAP_TO_START
//            }
//        }
//
//        smoothScroller.targetPosition = 0
//        (recycler_view.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
//         binding.recyclerView.smoothSnapToPosition(0)
    }

    fun setOnRecyclerItemClickedListener(listener: RecyclerItemClickListener) {
        adapter.listener = listener
    }

    fun bind(data: List<ViewHolderModel>, listener: RecyclerItemClickListener? = null) {
        adapter.updateItemsList(data)
        listener?.let {
           adapter.listener = it
        }
    }

    private fun initializeRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = dataList?.let { RecyclerAdapter(it) } ?: RecyclerAdapter(emptyList())
        binding.recyclerView.adapter = adapter

        listener?.let {
            this.listener = it
        }
    }

    companion object {
        private const val ZERO = 0
    }
}
