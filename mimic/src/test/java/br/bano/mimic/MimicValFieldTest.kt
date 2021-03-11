package br.bano.mimic

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class MimicValFieldTest {

    @Test
    fun testValFields_shouldCreateFakeList() {
        val list = MimicValObj::class.generateList(size = 10, typeSequence = listOf(Type(MimicValObj2::class)))
        assertEquals(10, list.size)
    }
}

data class MimicValObj(
    val string1: String,
    val int1: Int,
    val isSomething: Boolean,
    val double1: Double,
    val float1: Float,
    val date1: Date,
    val date2: Calendar,
    val long1: Long,
    val obj: MimicValObj2,
    val list: List<MimicValObj2>
)

data class MimicValObj2(
    val string1: String,
    val int1: Int,
    val isSomething: Boolean,
    val double1: Double,
    val float1: Float,
    val date1: Date,
    val date2: Calendar,
    val long1: Long
)
