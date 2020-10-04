package co.zemogaapp.posts.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import co.zemogaapp.R
import co.zemogaapp.common.constants.POST_TYPE
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.LayoutViewHolder
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.State

/**
 * Created by diego.urrea on 10/3/2020.
 */
class PostDA(private val listener: ActionListener): DelegateAdapter<PostDA.ViewHolder, Post> {

    interface ActionListener {
        fun onClick(item: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: Post) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_posts) {

        private val title: TextView = itemView.findViewById(R.id.postsTitle)
        private val description: TextView = itemView.findViewById(R.id.postsDescription)
        private val postsState: TextView = itemView.findViewById(R.id.postsState)
        private val header: ConstraintLayout = itemView.findViewById(R.id.postsHeader)

        fun bind(item: Post) {

            when(item.state) {
                State.READ -> {
                    // No op
                }
                State.UNREAD -> {
                    header.setBackgroundResource(R.color.unread)
                    postsState.apply {
                        text = context.getString(R.string.unread)
                        visibility = View.VISIBLE
                    }
                }
                State.FAVORITED -> {
                    header.setBackgroundResource(R.color.favorited)
                    postsState.apply {
                        text = context.getString(R.string.favorited)
                        visibility = View.VISIBLE
                    }
                }
            }

            title.text = item.title.capitalize()
            description.text = item.body.capitalize()

            itemView.setOnClickListener {
                listener.onClick(item)
            }

        }
    }

    companion object {
        const val VIEW_TYPE = POST_TYPE
    }
}
