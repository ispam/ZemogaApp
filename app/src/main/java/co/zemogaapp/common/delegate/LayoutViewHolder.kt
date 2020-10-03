package co.zemogaapp.common.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by diego.urrea on 10/03/2020.
 */
open class LayoutViewHolder(parent: ViewGroup, @LayoutRes layoutRes: Int)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))