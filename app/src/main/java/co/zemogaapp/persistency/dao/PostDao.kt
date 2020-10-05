package co.zemogaapp.persistency.dao

import androidx.room.Dao
import androidx.room.Query
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.State

/**
 * Created by diego.urrea on 10/4/2020.
 */
@Dao
interface PostDao: BaseDao<Post> {

    @Query("select count(*) from post")
    fun getTotalPosts(): Int

    @Query("select * from post")
    fun getAllPosts(): List<Post>

    @Query("update post set state = :state where id = :id")
    fun updatePostState(state: State, id: Int)

    @Query("delete from post")
    fun deleteAllPosts()

    @Query("select * from post where id = :id")
    fun getPost(id: Int): Post

    @Query("select * from post where state = :state")
    fun getAllFavoritedPosts(state: State): List<Post>

}
