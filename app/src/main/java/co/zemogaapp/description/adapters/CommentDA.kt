package co.zemogaapp.description.adapters

import android.view.ViewGroup
import android.widget.TextView
import co.zemogaapp.R
import co.zemogaapp.common.constants.COMMENT_TYPE
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.LayoutViewHolder
import co.zemogaapp.description.data.entities.Comment

/**
 * Created by diego.urrea on 10/4/2020.
 */
class CommentDA : DelegateAdapter<CommentDA.ViewHolder, Comment> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: Comment) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_comment) {

        private val text: TextView = itemView.findViewById(R.id.commentText)
        private val emailText: TextView = itemView.findViewById(R.id.commentEmail)

        fun bind(item: Comment) {
            with(item) {
                text.text = body.capitalize()
                emailText.text = email.capitalize()
            }
        }
    }

    companion object {
        const val VIEW_TYPE = COMMENT_TYPE
    }
}
