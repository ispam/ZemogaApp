package co.zemogaapp.posts.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.R
import co.zemogaapp.common.bases.BaseFragment
import co.zemogaapp.posts.adapters.FavoriteAdapter
import co.zemogaapp.posts.adapters.PostDA
import co.zemogaapp.posts.data.entities.FavoriteState
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.view_model.FavoriteViewModel
import co.zemogaapp.utils.extensions.clear
import co.zemogaapp.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.favoritesRecycler
import kotlinx.android.synthetic.main.fragment_posts.emptyView

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(), PostDA.ActionListener {

    private val viewModel by viewModels<FavoriteViewModel>()

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun layoutId(): Int = R.layout.fragment_favorites

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getFavoriteState(), ::onStateChange)
        setUpAdapters()
        viewModel.startFavoritesFlow()
    }

    private fun onStateChange(state: FavoriteState?) {
        when (state) {
            is FavoriteState.Empty -> {
                emptyView.visibility = View.VISIBLE
            }
            is FavoriteState.Initialized -> {
                emptyView.visibility = View.GONE
                favoriteAdapter.setInitialData(state.list)
            }
            is FavoriteState.Success -> {
                viewModel.toDescription(state.data)
            }
        }
    }

    override fun onClick(item: Post) {
        viewModel.getPostInfo(item)
    }

    private fun setUpAdapters() {
        favoriteAdapter = FavoriteAdapter(this)
        favoritesRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setItemViewCacheSize(100)
            adapter = favoriteAdapter
        }
    }

    override fun onDestroy() {
        releaseResources()
        super.onDestroy()
    }

    private fun releaseResources() {
        favoritesRecycler.clear()
    }
}