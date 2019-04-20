package br.bano.mimic

import br.bano.mimic.annotation.*
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class MimicUnitTest {
    @Test
    fun generateList_withOnlyMimicAnnotationEnable() {
        val list = Object1::class.java.generateList(10000, mimicAnnotationOnly = true)
        assertEquals(10000, list.size)
        list.forEach {
            assertNotNull(it.string1)
            assertNull(it.string2)

            val valueLength = it.string1?.length ?: 0
            assert(valueLength in 1..MAX_STRING_LENGTH)
            assertNull(it.string2)

            assert(!(it.string1?.contains("-") ?: false))
            assert(!(it.string2?.contains("-") ?: false))

            assert(it.int1 in 1..MAX_INT_SIZE)
            assertEquals(0, it.int2)

            assert(it.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
            assert(it.double2 == 0.0)

            assert(it.float1 > 0.0f)
            assert(it.float2 == 0.0f)

            assertNotNull(it.date1)
            assertNull(it.date2)

            assert(it.long1 in 1..MAX_LONG_SIZE)
            assertEquals(0, it.long2)

            assert(it.stringId1?.contains("-") ?: false)
            assert(it.intId1 > MAX_INT_SIZE)
            assert(it.longId1 > MAX_LONG_SIZE)
        }

        assert(list.any { it.isSomething })
        assert(list.any { !it.isSomething })
        assert(!list.any { it.isSomething2 })
    }

    @Test
    fun generateList_withOnlyMimicAnnotationDisable() {
        val list = Object1::class.java.generateList(10000, mimicAnnotationOnly = false)
        assertEquals(10000, list.size)
        list.forEach {
            assertNotNull(it.string1)
            assertNotNull(it.string2)

            val valueLength = it.string1?.length ?: 0
            assert(valueLength in 1..MAX_STRING_LENGTH)
            assert(it.string2?.length ?: 0 in 1..MAX_STRING_LENGTH)

            assert(!(it.string1?.contains("-") ?: false))
            assert(!(it.string2?.contains("-") ?: false))

            assert(it.int1 in 1..MAX_INT_SIZE)
            assert(it.int2 in 1..MAX_INT_SIZE)

            assert(it.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
            assert(it.double2 in 0.1..MAX_DOUBLE_SIZE + 1.0)

            assert(it.float1 > 0.0f)
            assert(it.float2 > 0.0f)

            assertNotNull(it.date1)
            assertNotNull(it.date2)

            assert(it.long1 in 1..MAX_LONG_SIZE)
            assert(it.long2 in 1..MAX_LONG_SIZE)

            assert(it.stringId1?.contains("-") ?: false)
            assert(it.intId1 > MAX_INT_SIZE)
            assert(it.longId1 > MAX_LONG_SIZE)
        }

        assert(list.any { it.isSomething })
        assert(list.any { !it.isSomething })
        assert(list.any { it.isSomething2 })
        assert(list.any { !it.isSomething2 })
    }

    class Object1 {
        @MimicRandom
        var string1: String? = null
        var string2: String? = null

        @MimicRandom
        var int1: Int = 0
        var int2: Int = 0

        @MimicRandom
        var isSomething: Boolean = false
        var isSomething2: Boolean = false

        @MimicRandom
        var double1: Double = 0.0
        var double2: Double = 0.0

        @MimicRandom
        var float1: Float = 0.0f
        var float2: Float = 0.0f

        @MimicRandom
        var date1: Date? = null
        var date2: Date? = null

        @MimicRandom
        var long1: Long = 0
        var long2: Long = 0

        @MimicStringId
        var stringId1: String? = null
        @MimicIntId
        var intId1: Int = 0
        @MimicLongId
        var longId1: Long = 0
    }
}