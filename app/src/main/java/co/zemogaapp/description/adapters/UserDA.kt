package co.zemogaapp.description.adapters

import android.view.ViewGroup
import android.widget.TextView
import co.zemogaapp.R
import co.zemogaapp.common.constants.USER_TYPE
import co.zemogaapp.common.delegate.DelegateAdapter
import co.zemogaapp.common.delegate.LayoutViewHolder
import co.zemogaapp.description.data.entities.User

/**
 * Created by diego.urrea on 10/4/2020.
 */
class UserDA : DelegateAdapter<UserDA.ViewHolder, User> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: User) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup): LayoutViewHolder(parent, R.layout.format_user) {

        private val nameText: TextView = itemView.findViewById(R.id.userName)
        private val emailText: TextView = itemView.findViewById(R.id.userEmail)
        private val websiteText: TextView = itemView.findViewById(R.id.userWebsite)
        private val phoneText: TextView = itemView.findViewById(R.id.userPhone)

        fun bind(item: User) {
            with(item) {
                nameText.text = name
                emailText.text = email
                websiteText.text = website
                phoneText.text = phone
            }
        }
    }

    companion object {
        const val VIEW_TYPE = USER_TYPE
    }
}
