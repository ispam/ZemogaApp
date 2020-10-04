package co.zemogaapp.utils

import android.os.Parcelable
import co.zemogaapp.description.data.entities.Comment
import co.zemogaapp.description.data.entities.Description
import co.zemogaapp.description.data.entities.User
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/4/2020.
 */
suspend fun foldFunctions(post: Post,
                          vararg functions: suspend (Post, SuccessData) -> PostState): PostState {

    val successData: SuccessData = functions.fold(SuccessData()) { updatedSuccessData, function ->
        val status = function(post, updatedSuccessData)
        if (status !is PostState.Continue) {
            return status
        }
        updatedSuccessData
    }

    val successModel = SuccessData()
    successData.description?.let { successModel.description = it }
    successData.user?.let { successModel.user = it }
    successData.comments?.let { successModel.comments = it }

    return PostState.Success(successModel)
}

@Parcelize
data class SuccessData(
    var description: Description? = null,
    var user: User? = null,
    var comments: List<Comment>? = null
): Parcelable
