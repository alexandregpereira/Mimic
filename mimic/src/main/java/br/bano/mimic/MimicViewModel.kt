package br.bano.mimic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class MimicViewModel<T : Any> : ViewModel() {

    protected val listLiveData = MutableLiveData<List<T>?>()
    protected val objLiveData = MutableLiveData<T?>()

    fun generateListLiveData(
        clazz: Class<T>,
        listSize: Int,
        mimicAnnotationOnly: Boolean = false
    ): LiveData<List<T>?> {
        getCoroutineScopeMain {
            listLiveData.value = clazz.generateList(listSize, mimicAnnotationOnly)
        }
        return listLiveData
    }

    fun generateObjLiveData(
        clazz: Class<T>,
        mimicAnnotationOnly: Boolean = false
    ): LiveData<T?> {
        getCoroutineScopeMain {
            objLiveData.value = clazz.generateObj(mimicAnnotationOnly)
        }
        return objLiveData
    }

    fun getListLiveData(): LiveData<List<T>?> = listLiveData

    fun getObjLiveData(): LiveData<T?> = objLiveData

    fun getList(): List<T>? = listLiveData.value

    fun getObj(): T? = objLiveData.value

    protected open fun getCoroutineScopeMain(block: suspend CoroutineScope.() -> Unit): Job =
        CoroutineScope(Dispatchers.Main).launch(block = block)
}