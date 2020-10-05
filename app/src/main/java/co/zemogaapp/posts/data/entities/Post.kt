package co.zemogaapp.posts.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.zemogaapp.common.constants.POST_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/3/2020.
 */
@Entity
@Parcelize
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var state: State = State.UNREAD
): RecyclerViewType, Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var post_internal_id: Long = 0

    override fun getViewType(): Int = POST_TYPE
}

enum class State(val state: Int) {
    READ(1), UNREAD(2), FAVORITED(3)
}
