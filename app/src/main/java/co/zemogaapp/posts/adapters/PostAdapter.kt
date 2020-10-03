package co.zemogaapp.posts.adapters

import co.zemogaapp.common.delegate.GenericRecyclerViewAdapter
import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.utils.extensions.toDA

/**
 * Created by diego.urrea on 10/3/2020.
 */
class PostAdapter(private val postAction: PostDA.ActionListener): GenericRecyclerViewAdapter<RecyclerViewType>() {

    init {
        delegateAdapters.apply {
            put(PostDA.VIEW_TYPE, PostDA(postAction).toDA())
        }
    }

    fun setInitialData(list: List<RecyclerViewType>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

}
