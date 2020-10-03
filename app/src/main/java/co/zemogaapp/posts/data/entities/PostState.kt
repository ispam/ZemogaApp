package co.zemogaapp.posts.data.entities

import co.zemogaapp.common.delegate.RecyclerViewType

/**
 * Created by diego.urrea on 10/3/2020.
 */
sealed class PostState {
    object Empty: PostState()
    class Initialized(val list: List<RecyclerViewType>): PostState()
}