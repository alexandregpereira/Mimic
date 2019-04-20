package br.bano.mimic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class MimicViewModel<T> : ViewModel() {

    private val listLiveData = MutableLiveData<T?>()

    fun getList(clazz: Class<T>): LiveData<T?> {
        getCoroutineScopeMain {

        }
        return listLiveData
    }

    fun getListLiveData(): LiveData<T?> {
        return listLiveData
    }

    protected open fun getCoroutineScopeMain(block: suspend CoroutineScope.() -> Unit): Job =
        CoroutineScope(Dispatchers.Main).launch(block = block)
}