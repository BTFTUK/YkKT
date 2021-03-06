package com.yk.demo.wan.android.ui.search

import android.text.TextUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseRecyclerAdapter
import com.yk.demo.wan.android.base.BaseViewHolder
import com.yk.demo.wan.android.data.SearchHistoryUtils
import com.yk.demo.wan.android.databinding.RecyclerHistoryBinding

/**
 * @author yk
 * @description
 */
typealias OnKeyRemove = () -> Unit

class HistoryAdapter(list: MutableList<String>? = null) : BaseRecyclerAdapter<String>(list) {
    var onKeyRemove: OnKeyRemove? = null

    override fun getLayoutId(viewType: Int): Int = R.layout.recycler_history

    override fun setVariable(data: String, position: Int, holder: BaseViewHolder<ViewDataBinding>) {
        (holder.binding as RecyclerHistoryBinding).let {
            it.history = data
            it.listener = View.OnClickListener {
                SearchHistoryUtils.removeKeyword(holder.binding.root.context, data)
                val last = mData?.size ?: 0
                mData?.remove(data)
                onKeyRemove?.invoke()
                notifyItemRangeChanged(0, last)
            }
        }
    }

    fun updateHistory(history: MutableList<String>) {
        val result = DiffUtil.calculateDiff(HistoryDiffCall(history, getAdapterData()), true)
        result.dispatchUpdatesTo(this)
        mData = (mData ?: arrayListOf()).apply {
            clear()
            addAll(history)
        }
    }
}

class HistoryDiffCall(private val newList: MutableList<String>?, private val oldList: MutableList<String>?) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        if (newList.isNullOrEmpty() || oldList.isNullOrEmpty()) false
        else TextUtils.equals(newList[newItemPosition], oldList[oldItemPosition])

    override fun getOldListSize(): Int = oldList?.size ?: 0

    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        if (newList.isNullOrEmpty() || oldList.isNullOrEmpty()) false
        else TextUtils.equals(newList[newItemPosition], oldList[oldItemPosition])
}