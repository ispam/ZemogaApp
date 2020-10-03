package co.zemogaapp.posts.data.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.utils.extensions.sendValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by diego.urrea on 10/3/2020.
 */

class PostViewModel
constructor(): ViewModel() {

    private var postState = MediatorLiveData<PostState>()
    fun getPostState(): LiveData<PostState> = postState

    fun startFlow() {

        viewModelScope.launch(Dispatchers.IO) {

            val list = mutableListOf<Post>().apply {
                for (num in 0..25) {
                    val position = num + 1
                    val state = if (num % 2 == 0) State.READ else State.FAVORITED
                    add(Post(position,
                        position,
                        "User $position",
                        "Email $position",
                        "Random text super random $position",
                        state))
                }
            }
            postState.sendValue(PostState.Initialized(list))
        }

    }

}
