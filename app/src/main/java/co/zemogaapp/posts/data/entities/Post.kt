package co.zemogaapp.posts.data.entities

import android.os.Parcelable
import co.zemogaapp.common.constants.POST_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/3/2020.
 */
@Parcelize
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var state: State = State.UNREAD
): RecyclerViewType, Parcelable {
    override fun getViewType(): Int = POST_TYPE
}

enum class State(val state: Int) {
    READ(1), UNREAD(2), FAVORITED(3)
}
