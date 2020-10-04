package co.zemogaapp.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by diego.urrea on 10/3/2020.
 */
inline fun <reified T: ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit = {}): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T: ViewModel> FragmentActivity.getViewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit = {}): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <T: Any, L: LiveData<T>> LifecycleOwner.observe(liveData: L, crossinline body: (T?) -> Unit) =
    liveData.observe(if (this is Fragment) viewLifecycleOwner else this, Observer { body(it) })

inline fun ViewModel.useViewModelScope(crossinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch (Dispatchers.IO) { block() }
}

suspend fun <T> MutableLiveData<T>.sendValue(item: T?) {
    withContext(Dispatchers.Main) {
        value = item
    }
}
