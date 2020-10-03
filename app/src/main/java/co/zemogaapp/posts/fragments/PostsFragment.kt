package co.zemogaapp.posts.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.R
import co.zemogaapp.common.bases.BaseFragment
import co.zemogaapp.utils.extensions.clear
import kotlinx.android.synthetic.main.fragment_posts.postsRecycler


class PostsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_posts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()



    }

    private fun setUpAdapter() {
        postsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onStop() {
        releaseResources()
        super.onStop()
    }

    private fun releaseResources() {
        postsRecycler.clear()
    }
}