package co.zemogaapp.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.zemogaapp.R
import co.zemogaapp.posts.fragments.MainFragment

/**
 * Main class for supporting posts functionality
 *
 * @author diego.urrea
 */
class MainActivity : AppCompatActivity() {

    private val mainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, mainFragment)
            .addToBackStack(mainFragment.tag)
            .commit()
    }
}