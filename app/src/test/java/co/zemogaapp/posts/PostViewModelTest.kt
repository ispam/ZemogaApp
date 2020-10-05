package co.zemogaapp.posts

import android.content.Context
import android.net.ConnectivityManager
import co.zemogaapp.persistency.dao.PostDao
import co.zemogaapp.posts.data.entities.Post
import co.zemogaapp.posts.data.entities.PostState
import co.zemogaapp.posts.data.view_model.PostViewModel
import co.zemogaapp.service.APIService
import co.zemogaapp.utils.BaseCoroutineViewModelTest
import co.zemogaapp.utils.SuccessData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by diego.urrea on 10/4/2020.
 */
class PostViewModelTest: BaseCoroutineViewModelTest<PostState>() {

    private lateinit var viewModel: PostViewModel

    private val service = mockk<APIService>()
    private val activity = mockk<MainActivity>(relaxed = true)
    private val dao = mockk<PostDao>()

    @ExperimentalCoroutinesApi
    @BeforeEach
    fun beforeEach() {
        viewModel = PostViewModel(service, activity, dao, testDispatcherProvider).apply {
            postState.observeForever(stateObserver)
        }

        val post1 = mockk<Post>()
        val post2 = mockk<Post>()
        val post3 = mockk<Post>()
        val list = mutableListOf<Post>().apply {
            add(post1)
            add(post2)
            add(post3)
        }
        every { dao.getTotalPosts() } returns 100
        every { dao.getAllPosts() } returns list
        every { dao.deleteAllPosts() } just runs

    }

    @Nested
    @DisplayName("Given user is in the posts screen")
    inner class PostFragment {

        @Test
        fun `when its the first time, then it should take show him an empty disclaimer`() {
            every { dao.getTotalPosts() } returns 0
            viewModel.startFlow()
            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(PostState.Empty::class)
        }

        @Test
        fun `when the user clicks on refresh data, then it should not show him a list of posts`() {
            val responseMock = mockk<Response<List<Post>>>(relaxUnitFun = true)
            every { responseMock.isSuccessful } returns false
            every { responseMock.body() } returns mockk()
            coEvery { service.getPosts() } returns responseMock

            viewModel.refreshAll()
            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(PostState.Error::class)
        }

        @Test
        fun `when the user has already refresh data, then it should show him a list of posts`() {
            viewModel.startFlow()
            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(PostState.Initialized::class)
        }

        @Test
        fun `when the user clicks on delete data, then it should remove all posts`() {
            viewModel.deleteAll()
            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(PostState.DeleteAll::class)
        }

        @Test
        fun `when the user clicks on a post, then it take him to description`() {
            every { activity.getSystemService(Context.CONNECTIVITY_SERVICE) } returns mockk<ConnectivityManager>(relaxed = true)
            val data = mockk<SuccessData>()
            viewModel.toDescription(data)
        }
    }

    private fun <T> MutableList<T>.assertStateOrder(vararg states: KClass<out Any>) {
        assertEquals(states.size, size)
        states.forEachIndexed { index, kClass ->
            assertTrue("Expected ${kClass.supertypes.first().toString().split(".").last()} " +
                    "$index to be ${kClass.simpleName}") {
                kClass.isInstance(this[index])
            }
        }
    }
}
