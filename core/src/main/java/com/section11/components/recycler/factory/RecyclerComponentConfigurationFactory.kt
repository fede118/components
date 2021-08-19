package com.section11.components.recycler.factory

internal data class RecyclerComponentConfiguration(
    val title: String?,
    val placeHolderItems: Int
)

internal object RecyclerComponentConfigurationFactory {

    fun create(recyclerComponentAttr: RecyclerComponentAttr): RecyclerComponentConfiguration {
        return RecyclerComponentConfiguration(
            title = recyclerComponentAttr.title,
            placeHolderItems = recyclerComponentAttr.placeHolderItems
        )
    }
}