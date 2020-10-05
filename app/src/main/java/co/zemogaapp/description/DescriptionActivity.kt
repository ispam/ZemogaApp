package co.zemogaapp.description

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.R
import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.description.adapters.CommentHeaderDA
import co.zemogaapp.description.adapters.DescriptionAdapter
import co.zemogaapp.description.data.entities.Description
import co.zemogaapp.description.data.entities.DescriptionState
import co.zemogaapp.description.data.view_model.DescriptionViewModel
import co.zemogaapp.posts.MainActivity
import co.zemogaapp.posts.data.entities.State
import co.zemogaapp.utils.SuccessData
import co.zemogaapp.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_description.descriptionRecycler

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private val descriptionAdapter = DescriptionAdapter()
    private val viewModel by viewModels<DescriptionViewModel>()
    private var isFavorited = false
    private var fullStar: Drawable? = null
    private var normalStar: Drawable? = null

    private lateinit var description: Description

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        observe(viewModel.getModelState(), ::onStateChange)
        fullStar = ContextCompat.getDrawable(this, R.drawable.ic_full_star)
        normalStar = ContextCompat.getDrawable(this, R.drawable.ic_white_star)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpAdapter()
        checkIntentDataAndInit()
    }

    private fun checkIntentDataAndInit() {
        intent?.let {
            if (it.hasExtra("data")) {
                val successData = it.getParcelableExtra<SuccessData>("data")

                val list = mutableListOf<RecyclerViewType>()
                successData?.let {
                    it.description?.let { description ->
                        description.state = State.READ
                        viewModel.setReadPostState(description)
                        this.description = description
                        list.add(description)
                    }
                    it.user?.let { user -> list.add(user) }
                    list.add(CommentHeaderDA.Model())
                    it.comments?.let { comments -> list.addAll(comments) }
                }
                descriptionAdapter.setInitialData(list)
            }
        }
    }

    private fun onStateChange(state: DescriptionState?) {
        when (state) {
            is DescriptionState.Favorited -> {
                Toast.makeText(this, "Post successfully favorited!!", Toast.LENGTH_SHORT).show()
            }
            is DescriptionState.Empty -> {}
            is DescriptionState.Error -> {}
        }
    }

    private fun setUpAdapter() {
        descriptionRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = descriptionAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.description_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this@apply)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favorite -> {
                if (::description.isInitialized) {
                    if (!isFavorited) {
                        item.icon = fullStar
                        isFavorited = true
                    } else {
                        item.icon = normalStar
                        isFavorited = false
                    }
                    viewModel.favoriteIconPressed(description)
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
