package co.zemogaapp.common.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by diego.urrea on 10/03/2020.
 */
interface DelegateAdapter<VH: RecyclerView.ViewHolder, T: RecyclerViewType> {
    fun onCreateViewHolder(parent: ViewGroup): VH

    fun onBindViewHolder(holder: VH, item: T)
}