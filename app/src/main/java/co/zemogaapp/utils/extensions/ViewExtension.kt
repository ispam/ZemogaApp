package co.zemogaapp.utils.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.RecyclerViewType

/**
 * Created by diego.urrea on 10/3/2020.
 */

fun RecyclerView?.clear() {
    this?.let {
        it.adapter = null
        it.layoutManager = null
    }
}

fun DelegateAdapter<*, *>.toDA(): DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>? {
    return this as? DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>
}

fun View.setAsVisible() {
    visibility = View.VISIBLE
}

fun View.setAsGone() {
    visibility = View.GONE
}

fun View.setAsInvisible() {
    visibility = View.INVISIBLE
}

fun View?.checkAndRelease() {
    this?.let {
        if (visibility == View.VISIBLE or View.INVISIBLE) {
            visibility = View.GONE
        }
    }
}