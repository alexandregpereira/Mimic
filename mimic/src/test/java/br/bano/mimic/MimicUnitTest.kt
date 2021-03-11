package br.bano.mimic

import org.junit.Assert.*
import org.junit.Test

class MimicUnitTest {

    @Test
    fun generateList_withOnlyMimicAnnotationEnable() {
        val list = Object1::class.generateList(MAX_LIST_SIZE, mimicAnnotationOnly = true)
        assertEquals(MAX_LIST_SIZE, list.size)
        list.forEach {
            assertObj_withOnlyMimicAnnotationEnable(it)
            val obj = it.obj
            if (obj != null) assertObj_withOnlyMimicAnnotationEnable(obj)
        }

        assert(list.any { it.isSomething })
        assert(list.any { !it.isSomething })
        assert(!list.any { it.isSomething2 })
    }

    @Test
    fun generateList_withOnlyMimicAnnotationDisable() {
        val list = Object1::class.generateList(MAX_LIST_SIZE, mimicAnnotationOnly = false)
        assertEquals(MAX_LIST_SIZE, list.size)
        list.forEach {
            assertObj_withOnlyMimicAnnotationDisable(it)
            val obj = it.obj
            if (obj != null) assertObj_withOnlyMimicAnnotationDisable(obj)
        }

        assert(list.any { it.isSomething })
        assert(list.any { !it.isSomething })
        assert(list.any { it.isSomething2 })
        assert(list.any { !it.isSomething2 })
    }

    @Test
    fun generateObj_withOnlyMimicAnnotationEnable() {
        val obj = Object1::class.generateObj(mimicAnnotationOnly = true)
        assertObj_withOnlyMimicAnnotationEnable(obj)
    }

    @Test
    fun generateObj_withOnlyMimicAnnotationDisable() {
        val obj = Object1::class.generateObj(mimicAnnotationOnly = false)
        assertObj_withOnlyMimicAnnotationDisable(obj)
    }

    @Test
    fun getList_withOnlyMimicAnnotationDisable_checkListInsideObjIsMocked() {
        val list = Object1::class.generateList(MAX_LIST_SIZE, mimicAnnotationOnly = false)
        list.forEach {
            assertEquals(MAX_SUBLIST_SIZE, it.objList?.size)
            it.objList?.forEach { obj2 ->
                assertObj_withOnlyMimicAnnotationDisable(obj2)
            }
        }
    }

    @Test
    fun getList_withOnlyMimicAnnotationEnable_checkListInsideObjIsMocked() {
        val list = Object1::class.generateList(MAX_LIST_SIZE, mimicAnnotationOnly = true)
        list.forEach {
            assertEquals(MAX_SUBLIST_SIZE, it.objList?.size)
            it.objList?.forEach { obj2 ->
                assertObj_withOnlyMimicAnnotationEnable(obj2)
            }
        }
    }

    @Test
    fun getObj_withOnlyMimicAnnotationDisable_checkListInsideObjIsMocked() {
        val obj = Object1::class.generateObj(mimicAnnotationOnly = false)
        assertEquals(MAX_SUBLIST_SIZE, obj.objList?.size)
        obj.objList?.forEach { obj2 ->
            assertObj_withOnlyMimicAnnotationDisable(obj2)
        }
    }

    @Test
    fun getObj_withOnlyMimicAnnotationEnable_checkListInsideObjIsMocked() {
        val obj = Object1::class.generateObj(mimicAnnotationOnly = true)
        assertEquals(MAX_SUBLIST_SIZE, obj.objList?.size)
        obj.objList?.forEach { obj2 ->
            assertObj_withOnlyMimicAnnotationEnable(obj2)
        }
    }

    private fun assertObj_withOnlyMimicAnnotationEnable(obj: MimicObj) {
        assertNotNull(obj.string1)
        assertNull(obj.string2)

        val valueLength = obj.string1?.length ?: 0
        assert(valueLength in 1..MAX_STRING_LENGTH)
        assertNull(obj.string2)
        assert(obj.string3?.length ?: 0 in 1..MAX_STRING_LENGTH_TEST)

        assert(obj.string1?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
        assert(obj.string3?.split(" ")?.size ?: 0 in MIN_WORDS_TEST..MAX_WORDS_TEST)

        assert(!(obj.string1?.contains("-") ?: false))
        assert(!(obj.string2?.contains("-") ?: false))
        assert(!(obj.string3?.contains("-") ?: false))

        assert(obj.int1 in 1..MAX_INT_SIZE)
        assertEquals(0, obj.int2)
        assert(obj.int3 in 1..MAX_INT_SIZE_TEST)

        assert(obj.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
        assert(obj.double2 == 0.0)
        assert(obj.double3 in 0.1..MAX_DOUBLE_SIZE_TEST + 1.0)

        assert(obj.float1 > 0.0f)
        assert(obj.float2 == 0.0f)

        assertEquals(MIN_TIME, obj.date1?.time)
        assertNull(obj.date2)
        assert(obj.date3?.time ?: 0 in MIN_TIME_TEST..MAX_TIME_TEST)

        assert(obj.long1 in 1..MAX_LONG_SIZE)
        assertEquals(0, obj.long2)
        assert(obj.long3 in 1..MAX_LONG_SIZE_TEST)

        assert(obj.stringId1?.contains("-") ?: false)
//        assert(obj.intId1 > MAX_INT_SIZE)
        assert(obj.longId1 > MAX_LONG_SIZE)
    }

    private fun assertObj_withOnlyMimicAnnotationDisable(obj: MimicObj) {
        assertNotNull(obj.string1)
        assertNotNull(obj.string2)

        val valueLength = obj.string1?.length ?: 0
        assert(valueLength in 1..MAX_STRING_LENGTH)
        assert(obj.string2?.length ?: 0 in 1..MAX_STRING_LENGTH)

        assert(obj.string1?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
        assert(obj.string2?.split(" ")?.size ?: 0 in MIN_WORDS..MAX_WORDS)
        assert(obj.string3?.split(" ")?.size ?: 0 in MIN_WORDS_TEST..MAX_WORDS_TEST)

        assert(!(obj.string1?.contains("-") ?: false))
        assert(!(obj.string2?.contains("-") ?: false))
        assert(!(obj.string3?.contains("-") ?: false))

        assert(obj.int1 in 1..MAX_INT_SIZE)
        assert(obj.int2 in 1..MAX_INT_SIZE)
        assert(obj.int3 in 1..MAX_INT_SIZE_TEST)

        assert(obj.double1 in 0.1..MAX_DOUBLE_SIZE + 1.0)
        assert(obj.double2 in 0.1..MAX_DOUBLE_SIZE + 1.0)
        assert(obj.double3 in 0.1..MAX_DOUBLE_SIZE_TEST + 1.0)

        assert(obj.float1 > 0.0f)
        assert(obj.float2 > 0.0f)

        assertEquals(MIN_TIME, obj.date1?.time)
        assertEquals(MIN_TIME, obj.date2?.time)
        assert(obj.date3?.time ?: 0 in MIN_TIME_TEST..MAX_TIME_TEST)

        assert(obj.long1 in 1..MAX_LONG_SIZE)
        assert(obj.long2 in 1..MAX_LONG_SIZE)
        assert(obj.long3 in 1..MAX_LONG_SIZE_TEST)

        assert(obj.stringId1?.contains("-") ?: false)
//        assert(obj.intId1 > MAX_INT_SIZE)
        assert(obj.longId1 > MAX_LONG_SIZE)
    }

    companion object {
        const val MAX_LIST_SIZE = 10000
        const val MAX_STRING_LENGTH_TEST = 80
        const val MAX_INT_SIZE_TEST = 10
        const val MAX_LONG_SIZE_TEST = 100L
        const val MAX_DOUBLE_SIZE_TEST = 10.0
        const val MIN_WORDS_TEST = 2
        const val MAX_WORDS_TEST = 4
        const val MIN_TIME_TEST = 1554163179000L
        const val MAX_TIME_TEST = 1554681579000L
        const val MAX_SUBLIST_SIZE = 12
    }
}