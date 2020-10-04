package co.zemogaapp.description.data.entities

import android.os.Parcelable
import co.zemogaapp.common.constants.COMMENT_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/4/2020.
 */
@Parcelize
data class Comment(val body: String, val email: String): RecyclerViewType, Parcelable {
    override fun getViewType(): Int = COMMENT_TYPE
}
