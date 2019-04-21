package br.bano.mimic

import br.bano.mimic.annotation.*
import java.util.*

class Object1 : MimicObj {
    @MimicRandom
    override var string1: String? = null
    override var string2: String? = null
    @MimicString(MimicUnitTest.MAX_STRING_LENGTH_TEST, MimicUnitTest.MIN_WORDS_TEST, MimicUnitTest.MAX_WORDS_TEST)
    override var string3: String? = null

    @MimicRandom
    override var int1: Int = 0
    override var int2: Int = 0
    @MimicInt(MimicUnitTest.MAX_INT_SIZE_TEST)
    override var int3: Int = 0

    @MimicRandom
    override var isSomething: Boolean = false
    override var isSomething2: Boolean = false

    @MimicRandom
    override var double1: Double = 0.0
    override var double2: Double = 0.0
    @MimicDouble(MimicUnitTest.MAX_DOUBLE_SIZE_TEST)
    override var double3: Double = 0.0

    @MimicRandom
    override var float1: Float = 0.0f
    override var float2: Float = 0.0f

    @MimicRandom
    override var date1: Date? = null
    override var date2: Date? = null
    @MimicDate(MimicUnitTest.MIN_TIME_TEST, MimicUnitTest.MAX_TIME_TEST)
    override var date3: Date? = null

    @MimicRandom
    override var long1: Long = 0
    override var long2: Long = 0
    @MimicLong(MimicUnitTest.MAX_LONG_SIZE_TEST)
    override var long3: Long = 0

    @MimicStringId
    override var stringId1: String? = null
    @MimicIntId
    override var intId1: Int = 0
    @MimicLongId
    override var longId1: Long = 0

    @MimicObject<Object2>(Object2::class)
    var obj: Object2? = null
}