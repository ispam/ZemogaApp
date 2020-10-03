package co.zemogaapp.common.delegate

import android.content.Context
import android.view.ViewGroup
import co.zemogaapp.R
import co.zemogaapp.common.constants.COMMON_EMPTY_TYPE
import javax.inject.Inject

/**
 * Created by diego.urrea on 10/03/2020.
 */
class EmptyView
@Inject constructor(val context: Context): DelegateAdapter<EmptyView.ViewHolder, EmptyView.Model> {

    private val DEFAULT_HEIGHT = context.resources.getDimensionPixelSize(R.dimen.normal)

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: Model) = holder.bind(item)

    data class Model(val heightPixel: Int = 0): RecyclerViewType {
        override fun getViewType(): Int = VIEW_TYPE
    }

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_empty_view) {
        fun bind(item: Model) {
            itemView.layoutParams.height = if (item.heightPixel != 0) item.heightPixel else DEFAULT_HEIGHT
        }
    }

    companion object {
        const val VIEW_TYPE = COMMON_EMPTY_TYPE
    }
}
