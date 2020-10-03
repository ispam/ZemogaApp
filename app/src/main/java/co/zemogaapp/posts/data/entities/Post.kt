package co.zemogaapp.posts.data.entities

import co.zemogaapp.common.constants.POST_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType

/**
 * Created by diego.urrea on 10/3/2020.
 */
data class Post(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    var state: State
): RecyclerViewType {
    override fun getViewType(): Int = POST_TYPE
}

enum class State(val state: Int) {
    READ(1), UNREAD(2), FAVORITED(3)
}
