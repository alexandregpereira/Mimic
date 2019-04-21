package br.bano.mimic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class MimicViewModel<T : Any> : ViewModel() {

    private val listLiveData = MutableLiveData<List<T>?>()
    private val objLiveData = MutableLiveData<T?>()

    fun getListLiveData(clazz: Class<T>, listSize: Int, mimicAnnotationOnly: Boolean = false): LiveData<List<T>?> {
        getCoroutineScopeMain {
            listLiveData.value = clazz.generateList(listSize, mimicAnnotationOnly)
        }
        return listLiveData
    }

    fun getObjLiveData(clazz: Class<T>, mimicAnnotationOnly: Boolean = false): LiveData<T?> {
        getCoroutineScopeMain {
            objLiveData.value = clazz.generateObj(mimicAnnotationOnly)
        }
        return objLiveData
    }

    fun getList(): List<T>? {
        return listLiveData.value
    }

    fun getObj(): T? {
        return objLiveData.value
    }

    protected open fun getCoroutineScopeMain(block: suspend CoroutineScope.() -> Unit): Job =
        CoroutineScope(Dispatchers.Main).launch(block = block)
}