package co.zemogaapp.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import co.zemogaapp.R
import co.zemogaapp.posts.adapters.MainPagerAdapter
import co.zemogaapp.posts.data.view_model.PostViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.mainFAB
import kotlinx.android.synthetic.main.activity_main.mainTabLayout
import kotlinx.android.synthetic.main.activity_main.mainViewPager
import kotlinx.android.synthetic.main.activity_main.toolbarRefresh

/**
 * Main class for supporting posts functionality
 *
 * @author diego.urrea
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = MainPagerAdapter(this)

        mainViewPager.apply {
            adapter = pagerAdapter
            TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.all_tab_layout)
                    1 -> tab.text = getString(R.string.favorites_tab_layout)
                }
            }.attach()
        }

        toolbarRefresh.setOnClickListener {
            viewModel.refreshAll()
        }

        mainFAB.setOnClickListener {
            viewModel.deleteAll()
        }
    }

}