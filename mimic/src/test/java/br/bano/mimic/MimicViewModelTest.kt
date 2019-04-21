package br.bano.mimic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MimicViewModelTest<T: Any> : MimicViewModel<T>() {

    override fun getCoroutineScopeMain(block: suspend CoroutineScope.() -> Unit): Job =
        runBlocking {
            this.launch(block = block)
        }
}