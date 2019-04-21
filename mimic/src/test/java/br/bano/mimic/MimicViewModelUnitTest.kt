package br.bano.mimic

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MimicViewModelUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun getList_mimicOnlyAnnotationDisable_checkListSize() {
        val viewModel = MimicViewModelTest<Object1>()
        viewModel.getListLiveData(Object1::class.java, 20000)
        assertEquals(20000, viewModel.getList()?.size)
    }

    @Test
    fun getList_mimicOnlyAnnotationEnable_checkListSize() {
        val viewModel = MimicViewModelTest<Object1>()
        viewModel.getListLiveData(Object1::class.java, 20000, true)
        assertEquals(20000, viewModel.getList()?.size)
    }
    @Test
    fun getObj_mimicOnlyAnnotationDisable_checkObj() {
        val viewModel = MimicViewModelTest<Object1>()
        viewModel.getObjLiveData(Object1::class.java)
        assertNotNull(viewModel.getObj())
        assertNotNull(viewModel.getObj()?.obj)
    }

    @Test
    fun getObj_mimicOnlyAnnotationEnable_checkObj() {
        val viewModel = MimicViewModelTest<Object1>()
        viewModel.getObjLiveData(Object1::class.java)
        assertNotNull(viewModel.getObj())
        assertNotNull(viewModel.getObj()?.obj)
    }
}