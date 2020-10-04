package co.zemogaapp.description.adapters

import android.view.ViewGroup
import android.widget.TextView
import co.zemogaapp.R
import co.zemogaapp.common.constants.COMMENT_HEADER_TYPE
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.LayoutViewHolder
import co.zemogaapp.common.delegate.RecyclerViewType

/**
 * Created by diego.urrea on 10/4/2020.
 */
class CommentHeaderDA : DelegateAdapter<CommentHeaderDA.ViewHolder, CommentHeaderDA.Model> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: Model) = holder.bind(item)

    data class Model(val text: String = "COMMENTS"): RecyclerViewType {
        override fun getViewType(): Int = VIEW_TYPE
    }

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_comment_header) {

        private val title: TextView = itemView.findViewById(R.id.headerTitle)

        fun bind(item: Model) {
            title.text = item.text
        }
    }

    companion object {
        const val VIEW_TYPE = COMMENT_HEADER_TYPE
    }
}
