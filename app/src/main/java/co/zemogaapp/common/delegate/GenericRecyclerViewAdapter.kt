package co.zemogaapp.common.delegate

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

/**
 * Created by diego.urrea on 10/03/2020.
 */
open class GenericRecyclerViewAdapter<T : RecyclerViewType> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    val items: MutableList<T> = arrayListOf()

    var delegateAdapters: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, T>>

    constructor() {
        delegateAdapters = SparseArrayCompat()
    }

    constructor(delegateAdapterMap: Map<Int, DelegateAdapter<*, *>>) {
        delegateAdapters = SparseArrayCompat(delegateAdapterMap.size)
        for ((key, value) in delegateAdapterMap) {
            delegateAdapters.put(key, value as DelegateAdapter<RecyclerView.ViewHolder, T>)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateAdapter = delegateAdapters.get(viewType)
            ?: throw IllegalStateException("The method onCreateViewHolder cannot return view type $viewType null")
        return delegateAdapter.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder, items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemId(position: Int): Long = position.toLong()
}