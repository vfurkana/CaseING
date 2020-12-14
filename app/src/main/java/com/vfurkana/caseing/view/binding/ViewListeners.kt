package com.vfurkana.caseing.view.binding

interface ItemClickListener<ModelType> {
    fun onItemClick(
        viewId: Int,
        modelList: List<ModelType>,
        clickedItem: ModelType,
        position: Int
    )
}

interface SelectedItemSelector<ModelType> {
    fun isSelected(item : ModelType): Boolean
}