package co.zemogaapp.description.adapters

import android.view.ViewGroup
import android.widget.TextView
import co.zemogaapp.R
import co.zemogaapp.common.constants.DESCRIPTION_TYPE
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.LayoutViewHolder
import co.zemogaapp.description.data.entities.Description

/**
 * Created by diego.urrea on 10/4/2020.
 */
class DescriptionDA : DelegateAdapter<DescriptionDA.ViewHolder, Description> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: Description) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_description) {

        private val descriptionText: TextView = itemView.findViewById(R.id.descriptionBody)

        fun bind(item: Description) {
            with(item) {
                descriptionText.text = body.capitalize()
            }
        }
    }

    companion object {
        const val VIEW_TYPE = DESCRIPTION_TYPE
    }
}
