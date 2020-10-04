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
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.service.APIService
import co.zemogaapp.utils.SuccessData
import co.zemogaapp.utils.extensions.sendValue
import co.zemogaapp.utils.foldFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by diego.urrea on 10/3/2020.
 */
class PostViewModel
@ViewModelInject constructor(private val service: APIService,
                             private val activity: FragmentActivity): ViewModel() {

    private var postState = MediatorLiveData<PostState>()
    fun getPostState(): LiveData<PostState> = postState

    fun startFlow() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = service.getPosts()
            if (response.isSuccessful) {
                response.body()?.let {
                    it.forEachIndexed { index, post ->
                        if (index < 20) {
                            post.state = State.UNREAD
                        } else {
                            post.state = State.READ
                        }
                    }
                    postState.sendValue(PostState.Initialized(it))
                }
            } else {
                postState.sendValue(PostState.Error)
            }
        }
    }

    suspend fun getUser(post: Post, successData: SuccessData): PostState {
        return try {
            val response = service.getUser(post.userId)
            if (response.isSuccessful) {
                successData.user = response.body()
            }
            PostState.Continue
        } catch (e: Exception) {
            PostState.Error
        }
    }

    suspend fun getPostComments(post: Post, successData: SuccessData): PostState {
        return try {
            val response = service.getComments(post.id)
            if (response.isSuccessful) {
                successData.comments = response.body()
                successData.description = Description(post.userId, post.id, post.title, post.body)
            }
            PostState.Continue
        } catch (e: Exception) {
            PostState.Error
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

    fun getPostInfo(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = foldFunctions(post, ::getUser, ::getPostComments)) {
                is PostState.Success -> {
                    postState.sendValue(PostState.Success(state.data))
                }
                is PostState.Error -> {
                    postState.sendValue(PostState.Error)
                }
                else -> {}
            }
        }
    }
}
