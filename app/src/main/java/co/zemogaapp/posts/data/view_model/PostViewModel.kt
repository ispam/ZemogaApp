package co.zemogaapp.posts.data.view_model

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zemogaapp.R
import co.zemogaapp.description.DescriptionActivity
import co.zemogaapp.description.data.entities.Description
import co.zemogaapp.persistency.dao.PostDao
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.service.APIService
import co.zemogaapp.utils.DispatcherProvider
import co.zemogaapp.utils.SuccessData
import co.zemogaapp.utils.extensions.sendValue
import co.zemogaapp.utils.foldFunctions
import co.zemogaapp.utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by diego.urrea on 10/3/2020.
 */
class PostViewModel
@ViewModelInject constructor(private val service: APIService,
                             private val activity: FragmentActivity,
                             private val postDao: PostDao,
                             private val dispatcher: DispatcherProvider): ViewModel() {

    internal var postState = MediatorLiveData<PostState>()
    fun getPostState(): LiveData<PostState> = postState

    fun startFlow() {
        viewModelScope.launch(dispatcher.io()) {

            val posts = postDao.getTotalPosts()
            if (posts in 1..99) {
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
                        postDao.insertAll(it)
                        postState.sendValue(PostState.Initialized(it))
                    }
                } else {
                    postState.sendValue(PostState.Error)
                }
            } else if (posts == 0) {
                postState.sendValue(PostState.Empty)
            } else {
                val list = postDao.getAllPosts()
                postState.sendValue(PostState.Initialized(list))
            }
        }
    }

    fun refreshAll() {
        viewModelScope.launch(dispatcher.io()) {
            postDao.deleteAllPosts()
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
                    postDao.insertAll(it)
                    postState.sendValue(PostState.Initialized(it))
                }
            } else {
                postState.sendValue(PostState.Error)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(dispatcher.io()) {
            postDao.deleteAllPosts()
            postState.sendValue(PostState.DeleteAll)
        }
    }

    private suspend fun getUser(post: Post, successData: SuccessData): PostState {
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

    private suspend fun getPostComments(post: Post, successData: SuccessData): PostState {
        return try {
            val response = service.getComments(post.id)
            if (response.isSuccessful) {
                successData.comments = response.body()
                successData.description = Description(post.userId, post.id, post.title, post.body, State.UNREAD)
            }
            PostState.Continue
        } catch (e: Exception) {
            PostState.Error
        }
    }

    fun toDescription(successData: SuccessData) {
        viewModelScope.launch(dispatcher.io()) {
            if (!isOnline(activity)) {
                postState.sendValue(PostState.Error)
                return@launch
            }
            Intent(activity, DescriptionActivity::class.java).apply {
                putExtra("data", successData)
                with(activity) {
                    startActivity(this@apply)
                }
            }
        }
    }

    fun getPostInfo(post: Post) {
        viewModelScope.launch(dispatcher.io()) {
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
