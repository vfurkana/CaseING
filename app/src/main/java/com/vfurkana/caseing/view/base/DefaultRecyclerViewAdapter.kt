package com.vfurkana.caseing.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.vfurkana.caseing.BR
import com.vfurkana.caseing.view.binding.ItemClickListener
import com.vfurkana.caseing.view.binding.SelectedItemSelector

class DefaultRecyclerViewAdapter<ItemType>(
    var modelList: List<ItemType>,
    val itemLayoutId: Int,
    val itemClickListener: ItemClickListener<ItemType>?,
    val selectedItemSelector: SelectedItemSelector<ItemType>?
) : RecyclerView.Adapter<DefaultRecyclerViewAdapter.BaseViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, itemLayoutId, parent, false)
        return object : BaseViewHolder(binding) {
            override fun bindData(position: Int) {
                val model = modelList[position]
                itemBinding.setVariable(BR.model, model)
                selectedItemSelector?.let { selector ->
                    itemBinding.setVariable(BR.selectedItemSelector, selector)
                }
                itemClickListener?.let { clickListener ->
                    setChildClickListener(itemBinding.root, clickListener, model, position)
                }
            }
        }
    }

    private fun setChildClickListener(view: View, onClickListener: ItemClickListener<ItemType>, model: ItemType, position: Int) {
        if (view is ViewGroup) {
            val root = view as ViewGroup?
            for (i in 0 until root!!.childCount) {
                val child = root.getChildAt(i)
                if (child != null) {
                    if (child is ViewGroup) {
                        setChildClickListener(child, onClickListener, model, position)
                    }
                    child.setOnClickListener {
                        onClickListener.onItemClick(it.id, modelList, model, position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemId(position: Int): Long {
        return modelList[position]?.hashCode()?.toLong() ?: 0L
    }

    fun updateData(list: List<ItemType>) {
        modelList = list
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder(
        val itemBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        abstract fun bindData(position: Int)

    }
}