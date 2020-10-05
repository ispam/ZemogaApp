package co.zemogaapp.description.data.entities

/**
 * Created by diego.urrea on 10/4/2020.
 */
sealed class DescriptionState {
    object Empty: DescriptionState()
    object Error: DescriptionState()
    object Favorited: DescriptionState()
}
