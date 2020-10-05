package co.zemogaapp.persistency.utils

import androidx.room.TypeConverter
import co.zemogaapp.posts.data.entities.State

/**
 * Created by diego.urrea on 10/4/2020.
 */
class StateConverter {
    @TypeConverter
    fun fromState(value: State): Int{
        return value.ordinal
    }

    @TypeConverter
    fun toState(value: Int): State {
        return when(value){
            0 -> State.READ
            1 -> State.UNREAD
            2 -> State.FAVORITED
            else -> State.READ
        }
    }
}
