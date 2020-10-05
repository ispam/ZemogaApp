package co.zemogaapp.persistency

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.zemogaapp.persistency.dao.PostDao
import co.zemogaapp.persistency.utils.StateConverter
import co.zemogaapp.posts.data.entities.Post

/**
 * Created by diego.urrea on 10/4/2020.
 */
@Database(entities = [Post::class], version = 1, exportSchema = false)
@TypeConverters(StateConverter::class)
abstract class ZemogaDB: RoomDatabase() {

    abstract fun postDao(): PostDao

}
