package co.zemogaapp.utils

import androidx.lifecycle.Observer
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

/**
 * Created by diego.urrea on 10/4/2020.
 */
@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseCoroutineViewModelTest<T> {

    var stateList = mutableListOf<T>()
    val stateObserver = mockk<Observer<T>>(relaxed = true)

    @ExperimentalCoroutinesApi
    val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    val testDispatcherProvider = object : DispatcherProvider {
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    @ExperimentalCoroutinesApi
    @JvmField
    @RegisterExtension
    val coroutineTestExtension = CoroutinesTestExtension(testDispatcher)

    @BeforeEach
    private fun resetStateFields() {
        clearMocks(stateObserver)
        stateList.clear()
    }

}
