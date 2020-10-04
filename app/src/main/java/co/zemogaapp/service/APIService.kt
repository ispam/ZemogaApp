package co.zemogaapp.service

import co.zemogaapp.description.data.entities.Comment
import co.zemogaapp.description.data.entities.User
import co.zemogaapp.posts.data.entities.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by diego.urrea on 10/3/2020.
 */
interface APIService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: Int): Response<User>

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: Int): Response<List<Comment>>
}
