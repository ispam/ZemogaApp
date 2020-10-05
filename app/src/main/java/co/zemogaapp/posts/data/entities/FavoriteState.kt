package co.zemogaapp.posts.data.entities

import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.utils.SuccessData

/**
 * Created by diego.urrea on 10/4/2020.
 */

sealed class FavoriteState {
    object Empty: FavoriteState()
    object Error: FavoriteState()
    object Continue: FavoriteState()
    class Initialized(val list: List<RecyclerViewType>): FavoriteState()
    class Success(val data: SuccessData): FavoriteState()
}