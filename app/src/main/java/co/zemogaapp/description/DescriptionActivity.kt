package co.zemogaapp.description

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zemogaapp.R
import co.zemogaapp.common.delegate.RecyclerViewType
import co.zemogaapp.description.adapters.CommentHeaderDA
import co.zemogaapp.description.adapters.DescriptionAdapter
import co.zemogaapp.utils.SuccessData
import kotlinx.android.synthetic.main.activity_description.descriptionRecycler

class DescriptionActivity : AppCompatActivity() {

    private val descriptionAdapter = DescriptionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        descriptionRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = descriptionAdapter
        }

        intent?.let {
            if (it.hasExtra("data")) {
                val successData = it.getParcelableExtra<SuccessData>("data")

                val list = mutableListOf<RecyclerViewType>()
                successData?.let {
                    it.description?.let { post -> list.add(post) }
                    it.user?.let { user -> list.add(user) }
                    list.add(CommentHeaderDA.Model())
                    it.comments?.let { comments -> list.addAll(comments) }
                }

                descriptionAdapter.setInitialData(list)
            }
        }
    }

}
