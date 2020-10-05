package co.zemogaapp.posts.data.view_model

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zemogaapp.description.DescriptionActivity
import co.zemogaapp.description.data.entities.Description
import co.zemogaapp.persistency.dao.PostDao
import co.zemogaapp.posts.data.entities.FavoriteState
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.service.APIService
import co.zemogaapp.utils.SuccessData
import co.zemogaapp.utils.extensions.sendValue
import co.zemogaapp.utils.foldFunctionsFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by diego.urrea on 10/4/2020.
 */
class FavoriteViewModel
@ViewModelInject constructor(private val postDao: PostDao,
                             private val activity: FragmentActivity,
                             private val service: APIService): ViewModel() {

    private var favoriteState = MediatorLiveData<FavoriteState>()
    fun getFavoriteState(): LiveData<FavoriteState> = favoriteState

    fun startFavoritesFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts = postDao.getAllFavoritedPosts(State.FAVORITED)
            if (posts.isNotEmpty()) {
                favoriteState.sendValue(FavoriteState.Initialized(posts))
            } else {
                favoriteState.sendValue(FavoriteState.Empty)
            }
        }
    }

    fun toDescription(successData: SuccessData) {
        Intent(activity, DescriptionActivity::class.java).apply {
            putExtra("data", successData)
            with(activity) {
                startActivity(this@apply)
            }
        }
    }

    private suspend fun getUser(post: Post, successData: SuccessData): FavoriteState {
        return try {
            val response = service.getUser(post.userId)
            if (response.isSuccessful) {
                successData.user = response.body()
            }
            FavoriteState.Continue
        } catch (e: Exception) {
            FavoriteState.Error
        }
    }

    private suspend fun getPostComments(post: Post, successData: SuccessData): FavoriteState {
        return try {
            val response = service.getComments(post.id)
            if (response.isSuccessful) {
                successData.comments = response.body()
                successData.description = Description(post.userId, post.id, post.title, post.body, State.UNREAD)
            }
            FavoriteState.Continue
        } catch (e: Exception) {
            FavoriteState.Error
        }
    }

    fun getPostInfo(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = foldFunctionsFavorite(post, ::getUser, ::getPostComments)) {
                is FavoriteState.Success -> {
                    favoriteState.sendValue(FavoriteState.Success(state.data))
                }
                is FavoriteState.Error -> {
                    favoriteState.sendValue(FavoriteState.Error)
                }
                else -> {}
            }
        }
    }
}
