package co.zemogaapp.posts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zemogaapp.R
import co.zemogaapp.common.bases.BaseFragment
import co.zemogaapp.posts.adapters.MainPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.mainTabLayout
import kotlinx.android.synthetic.main.fragment_main.mainViewPager


class MainFragment : BaseFragment() {

    private lateinit var pagerAdapter: MainPagerAdapter

    override fun layoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = MainPagerAdapter(this)

        mainViewPager.apply {
            adapter = pagerAdapter
            TabLayoutMediator(mainTabLayout, this) { tab, position ->
                when (position) {
                    0 -> tab.text = context.getString(R.string.all_tab_layout)
                    1 -> tab.text = context.getString(R.string.favorites_tab_layout)
                }
            }.attach()
        }

    }
}
