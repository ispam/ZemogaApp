package co.zemogaapp.posts.data.entities

import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.utils.SuccessData

/**
 * Created by diego.urrea on 10/3/2020.
 */
sealed class PostState {
    object Empty: PostState()
    object Error: PostState()
    object Continue: PostState()
    object DeleteAll: PostState()
    class Initialized(val list: List<RecyclerViewType>): PostState()
    class Success(val data: SuccessData): PostState()
}