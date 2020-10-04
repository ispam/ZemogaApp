package co.zemogaapp.description.adapters

import co.zemogaapp.common.delegate.GenericRecyclerViewAdapter
import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.utils.extensions.toDA

/**
 * Created by diego.urrea on 10/4/2020.
 */
class DescriptionAdapter: GenericRecyclerViewAdapter<RecyclerViewType>() {

    init {
        delegateAdapters.apply {
            put(DescriptionDA.VIEW_TYPE, DescriptionDA().toDA())
            put(UserDA.VIEW_TYPE, UserDA().toDA())
            put(CommentHeaderDA.VIEW_TYPE, CommentHeaderDA().toDA())
            put(CommentDA.VIEW_TYPE, CommentDA().toDA())
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
