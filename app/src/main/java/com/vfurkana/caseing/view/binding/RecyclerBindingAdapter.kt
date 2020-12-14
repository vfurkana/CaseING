package com.vfurkana.caseing.view.base

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.vfurkana.caseing.view.binding.ItemClickListener
import com.vfurkana.caseing.view.binding.SelectedItemSelector

@BindingAdapter(
    value = ["itemList", "itemLayoutId", "dividerEnabled", "itemClickListener", "dividerDrawableId", "snapEnabled", "selectedItemSelector"],
    requireAll = false
)
fun bindRecyclerView(
    recyclerView: RecyclerView,
    itemList: List<Nothing>?,
    itemLayoutId: Int,
    dividerEnabled: Boolean,
    itemClickListener: ItemClickListener<Nothing>?,
    dividerDrawableId: Drawable?,
    snapEnabled: Boolean,
    selectedItemSelector: SelectedItemSelector<Nothing>?
) {
    if (itemList == null) return

    if (recyclerView.adapter == null) {
        val adapter = createAdapter(itemList, itemLayoutId, itemClickListener, selectedItemSelector)
        setDefaultLayoutManager(recyclerView)
        recyclerView.adapter = adapter
        addSnapHelper(recyclerView, snapEnabled)
        showDefaultDivider(dividerEnabled, recyclerView)
        addDividerDrawable(dividerDrawableId, recyclerView)
    } else {
        val adapter = recyclerView.adapter as DefaultRecyclerViewAdapter<*>
        adapter.updateData(itemList)
    }
}


private fun setDefaultLayoutManager(recyclerView: RecyclerView) {
    if (recyclerView.layoutManager != null) return
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
}

private fun addSnapHelper(recyclerView: RecyclerView, snapEnabled: Boolean) {
    if (!snapEnabled) return
    val snapHelper = LinearSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
}

private fun showDefaultDivider(dividerEnabled: Boolean, recyclerView: RecyclerView) {
    if (!dividerEnabled) return
    val dividerItemDecoration = DividerItemDecoration(
        recyclerView.context,
        RecyclerView.VERTICAL
    )
    recyclerView.addItemDecoration(dividerItemDecoration)
}

private fun addDividerDrawable(dividerDrawable: Drawable?, recyclerView: RecyclerView) {
    if (dividerDrawable == null) return
    val dividerItemDecoration = DividerItemDecoration(
        recyclerView.context,
        RecyclerView.VERTICAL
    )
    dividerItemDecoration.setDrawable(dividerDrawable)
    recyclerView.addItemDecoration(dividerItemDecoration)
}

private fun createAdapter(
    itemList: List<Nothing>,
    layoutId: Int,
    itemClickListener: ItemClickListener<Nothing>?,
    selectedItemSelector: SelectedItemSelector<Nothing>?
): DefaultRecyclerViewAdapter<Nothing> {
    return DefaultRecyclerViewAdapter(itemList, layoutId, itemClickListener, selectedItemSelector)
}