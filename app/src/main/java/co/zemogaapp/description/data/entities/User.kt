package co.zemogaapp.description.data.entities

import android.os.Parcelable
import co.zemogaapp.common.constants.USER_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/4/2020.
 */
@Parcelize
data class User(val name: String,
                val email: String,
                val website: String,
                val phone: String): RecyclerViewType, Parcelable {
    override fun getViewType(): Int = USER_TYPE
}
