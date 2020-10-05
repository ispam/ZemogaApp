package co.zemogaapp.description.data.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zemogaapp.description.data.entities.Description
import co.zemogaapp.description.data.entities.DescriptionState
import co.zemogaapp.persistency.dao.PostDao
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.utils.extensions.sendValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by diego.urrea on 10/4/2020.
 */
class DescriptionViewModel
@ViewModelInject constructor(private val postDao: PostDao): ViewModel() {

    private var modelState = MediatorLiveData<DescriptionState>()
    fun getModelState(): LiveData<DescriptionState> = modelState

    fun favoriteIconPressed(description: Description) {
        viewModelScope.launch(Dispatchers.IO) {
            postDao.updatePostState(State.FAVORITED, description.id)
            modelState.sendValue(DescriptionState.Favorited)
        }
    }

    fun setReadPostState(description: Description) {
        viewModelScope.launch(Dispatchers.IO) {
            val post = postDao.getPost(description.id)
            if (post.state != State.FAVORITED) {
                postDao.updatePostState(State.READ, description.id)
            }
        }
    }
}
