package co.zemogaapp.posts.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.zemogaapp.posts.fragments.FavoritesFragment
import co.zemogaapp.posts.fragments.PostsFragment

/**
 * Created by diego.urrea on 10/3/2020.
 */
class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_ITEMS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostsFragment()
            1 -> FavoritesFragment()
            else -> PostsFragment()
        }
    }

    companion object {
        private const val NUM_ITEMS = 2
    }
}
