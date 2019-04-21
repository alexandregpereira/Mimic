package br.bano.mimic

import br.bano.mimic.annotation.*
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class MimicUnitTest {
    @Test
    fun generateList_withOnlyMimicAnnotationEnable() {
        val list = Object1::class.java.generateList(30000, mimicAnnotationOnly = true)
        assertEquals(30000, list.size)
        list.forEach {
            assertNotNull(it.string1)
            assertNull(it.string2)

            val valueLength = it.string1?.length ?: 0
            assert(valueLength in 1..MAX_STRING_LENGTH)
            assertNull(it.string2)
            assert(it.string3?.length ?: 0 in 1..MAX_STRING_LENGTH_TEST)

            assert(it.string1?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
            assert(it.string3?.split(" ")?.size ?: 0 in MIN_WORDS_TEST..MAX_WORDS_TEST)

            assert(!(it.string1?.contains("-") ?: false))
            assert(!(it.string2?.contains("-") ?: false))
            assert(!(it.string3?.contains("-") ?: false))

            assert(it.int1 in 1..MAX_INT_SIZE)
            assertEquals(0, it.int2)
            assert(it.int3 in 1..MAX_INT_SIZE_TEST)

            assert(it.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
            assert(it.double2 == 0.0)
            assert(it.double3 in 0.1..MAX_DOUBLE_SIZE_TEST + 1.0)

            assert(it.float1 > 0.0f)
            assert(it.float2 == 0.0f)

            assertEquals(MIN_TIME, it.date1?.time)
            assertNull(it.date2)
            assert(it.date3?.time ?: 0 in MIN_TIME_TEST..MAX_TIME_TEST)

            assert(it.long1 in 1..MAX_LONG_SIZE)
            assertEquals(0, it.long2)
            assert(it.long3 in 1..MAX_LONG_SIZE_TEST)

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
        val list = Object1::class.java.generateList(30000, mimicAnnotationOnly = false)
        assertEquals(30000, list.size)
        list.forEach {
            assertNotNull(it.string1)
            assertNotNull(it.string2)

            val valueLength = it.string1?.length ?: 0
            assert(valueLength in 1..MAX_STRING_LENGTH)
            assert(it.string2?.length ?: 0 in 1..MAX_STRING_LENGTH)

            assert(it.string1?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
            assert(it.string2?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
            assert(it.string3?.split(" ")?.size ?: 0 in MIN_WORDS_TEST..MAX_WORDS_TEST)

            assert(!(it.string1?.contains("-") ?: false))
            assert(!(it.string2?.contains("-") ?: false))
            assert(!(it.string3?.contains("-") ?: false))

            assert(it.int1 in 1..MAX_INT_SIZE)
            assert(it.int2 in 1..MAX_INT_SIZE)
            assert(it.int3 in 1..MAX_INT_SIZE_TEST)

            assert(it.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
            assert(it.double2 in 0.1..MAX_DOUBLE_SIZE + 1.0)
            assert(it.double3 in 0.1..MAX_DOUBLE_SIZE_TEST + 1.0)

            assert(it.float1 > 0.0f)
            assert(it.float2 > 0.0f)

            assertEquals(MIN_TIME, it.date1?.time)
            assertEquals(MIN_TIME, it.date2?.time)
            assert(it.date3?.time ?: 0 in MIN_TIME_TEST..MAX_TIME_TEST)

            assert(it.long1 in 1..MAX_LONG_SIZE)
            assert(it.long2 in 1..MAX_LONG_SIZE)
            assert(it.long3 in 1..MAX_LONG_SIZE_TEST)

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
        @MimicString(MAX_STRING_LENGTH_TEST, MIN_WORDS_TEST, MAX_WORDS_TEST)
        var string3: String? = null

        @MimicRandom
        var int1: Int = 0
        var int2: Int = 0
        @MimicInt(MAX_INT_SIZE_TEST)
        var int3: Int = 0

        @MimicRandom
        var isSomething: Boolean = false
        var isSomething2: Boolean = false

        @MimicRandom
        var double1: Double = 0.0
        var double2: Double = 0.0
        @MimicDouble(MAX_DOUBLE_SIZE_TEST)
        var double3: Double = 0.0

        @MimicRandom
        var float1: Float = 0.0f
        var float2: Float = 0.0f

        @MimicRandom
        var date1: Date? = null
        var date2: Date? = null
        @MimicDate(MIN_TIME_TEST, MAX_TIME_TEST)
        var date3: Date? = null

        @MimicRandom
        var long1: Long = 0
        var long2: Long = 0
        @MimicLong(MAX_LONG_SIZE_TEST)
        var long3: Long = 0

        @MimicStringId
        var stringId1: String? = null
        @MimicIntId
        var intId1: Int = 0
        @MimicLongId
        var longId1: Long = 0
    }

    companion object {
        const val MAX_STRING_LENGTH_TEST = 80
        const val MAX_INT_SIZE_TEST = 10
        const val MAX_LONG_SIZE_TEST = 100L
        const val MAX_DOUBLE_SIZE_TEST = 10.0
        const val MIN_WORDS_TEST = 2
        const val MAX_WORDS_TEST = 4
        const val MIN_TIME_TEST = 1554163179000L
        const val MAX_TIME_TEST = 1554681579000L
    }
}