package co.zemogaapp.posts.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.R
import co.zemogaapp.common.bases.BaseFragment
import co.zemogaapp.posts.adapters.PostAdapter
import co.zemogaapp.posts.adapters.PostDA
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import co.zemogaapp.posts.data.view_model.PostViewModel
import co.zemogaapp.utils.extensions.clear
import co.zemogaapp.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.postsRecycler

@AndroidEntryPoint
class PostsFragment : BaseFragment(), PostDA.ActionListener {

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var postAdapter: PostAdapter

    override fun layoutId(): Int = R.layout.fragment_posts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getPostState(), ::onStateChange)
        setUpAdapters()
        viewModel.startFlow()
    }

    private fun onStateChange(state: PostState?) {
        when (state) {
            is PostState.Empty -> {}
            is PostState.Error -> {}
            is PostState.Success -> {
                viewModel.toDescription(state.data)
            }
            is PostState.Initialized -> postAdapter.setInitialData(state.list)
        }
    }

    override fun onClick(item: Post) {
        viewModel.getPostInfo(item)
    }

    private fun setUpAdapters() {
        postAdapter = PostAdapter(this)
        postsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setItemViewCacheSize(100)
            adapter = postAdapter
        }
    }

    override fun onDestroy() {
        releaseResources()
        super.onDestroy()
    }

    private fun releaseResources() {
        postsRecycler.clear()
    }
}