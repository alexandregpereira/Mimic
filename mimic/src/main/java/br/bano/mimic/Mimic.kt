package br.bano.mimic

import br.bano.mimic.annotation.*
import com.thedeanda.lorem.LoremIpsum
import java.lang.reflect.Field
import java.util.*
import kotlin.random.Random

const val MAX_STRING_LENGTH = 150
const val MAX_INT_SIZE = 1000
const val MAX_LONG_SIZE = 1000000L
const val MAX_DOUBLE_SIZE = 1000.0
const val MIN_WORDS = 1
const val MAX_WORDS = 30
const val MIN_TIME = 1555804864884L
const val MAX_TIME = 1555804864884L

fun <T : Any> Class<T>.generateList(size: Int, mimicAnnotationOnly: Boolean = false): List<T> {
    return (0 until size).map {
        this.generateObj(mimicAnnotationOnly, avoidSubLists = false)
    }
}

fun <T : Any> Class<T>.generateObj(mimicAnnotationOnly: Boolean = false, avoidSubLists: Boolean = false): T {
    val obj: T = try {
        this.getConstructor().newInstance()
    } catch (ex: NoSuchMethodException) {
        throw NoSuchMethodException("$this must have a empty constructor")
    }

    val lorem = LoremIpsum.getInstance()
    obj::class.java.declaredFields.forEach { field ->
        field.isAccessible = true
        when {
            isMimicStringId(field) -> setStringIdField(obj, field)
            isMimicString(field, mimicAnnotationOnly) -> setStringField(obj, field, lorem)
            isMimicIntId(field) -> setIntIdField(obj, field)
            isMimicInt(field, mimicAnnotationOnly) -> setIntField(obj, field)
            isMimicLongId(field) -> setLongIdField(obj, field)
            isMimicLong(field, mimicAnnotationOnly) -> setLongField(obj, field)
            isMimicDouble(field, mimicAnnotationOnly) -> setDoubleField(obj, field)
            field.isMimic(Float::class.java, mimicAnnotationOnly) -> setFloatField(obj, field)
            field.isMimic(Boolean::class.java, mimicAnnotationOnly) -> setBooleanField(obj, field)
            isMimicDate(field, mimicAnnotationOnly) -> setDateField(obj, field)
            isMimicObject(field) -> setObjectField(obj, field, mimicAnnotationOnly)
            !avoidSubLists && isMimicList(field) -> setListField(obj, field, mimicAnnotationOnly)
        }
    }

    return obj
}

private fun isMimicString(field: Field, mimicAnnotationOnly: Boolean) =
    field.isMimic(String::class.java, mimicAnnotationOnly) { it is MimicString || it is MimicRandom }

private fun isMimicStringId(field: Field) =
    field.isMimic(String::class.java) { it is MimicStringId }

private fun isMimicInt(field: Field, mimicAnnotationOnly: Boolean) =
    field.isMimic(Int::class.java, mimicAnnotationOnly) { it is MimicInt || it is MimicRandom }

private fun isMimicIntId(field: Field) =
    field.isMimic(Int::class.java) { it is MimicIntId }

private fun isMimicLong(field: Field, mimicAnnotationOnly: Boolean) =
    field.isMimic(Long::class.java, mimicAnnotationOnly) { it is MimicLong || it is MimicRandom }

private fun isMimicLongId(field: Field) =
    field.isMimic(Long::class.java) { it is MimicLongId }

private fun isMimicDouble(field: Field, mimicAnnotationOnly: Boolean) =
    field.isMimic(Double::class.java, mimicAnnotationOnly) { it is MimicDouble || it is MimicRandom }

private fun isMimicDate(field: Field, mimicAnnotationOnly: Boolean) =
    field.isMimic(Date::class.java, mimicAnnotationOnly) { it is MimicDate || it is MimicRandom }

private fun isMimicObject(field: Field) =
    field.annotations.any { it is MimicObject<*> }

private fun isMimicList(field: Field) =
    field.annotations.any { it is MimicList<*> }

private fun Field.isMimic(
    clazz: Class<*>,
    mimicAnnotationOnly: Boolean = true,
    predicated: ((Annotation) -> Boolean) = { it is MimicRandom }
) =
    this.type.isAssignableFrom(clazz) &&
            (!mimicAnnotationOnly ||
                    this.annotations.any(predicated))

private fun <T : Any> setStringField(obj: T, field: Field, lorem: LoremIpsum) {
    val stringAnnotation = field.annotations.find { it is MimicString } as? MimicString
    val maxLength = stringAnnotation?.maxLength ?: MAX_STRING_LENGTH
    val minWords = stringAnnotation?.minWords ?: MIN_WORDS
    val maxWords = stringAnnotation?.maxWords ?: MAX_WORDS

    val words = lorem.getWords(minWords, maxWords)
    field.set(
        obj,
        if (words.length > maxLength)
            words.substring(0, maxLength).trim()
        else
            words
    )
}

private fun <T : Any> setStringIdField(obj: T, field: Field) {
    field.set(obj, UUID.randomUUID().toString())
}

private fun <T : Any> setIntField(obj: T, field: Field) {
    val max = (field.annotations.find { it is MimicInt } as? MimicInt)?.max ?: MAX_INT_SIZE
    field.setInt(obj, Random.nextInt(max) + 1)
}

private fun <T : Any> setIntIdField(obj: T, field: Field) {
    field.setInt(obj, Date().time.toInt())
}

private fun <T : Any> setLongField(obj: T, field: Field) {
    val max = (field.annotations.find { it is MimicLong } as? MimicLong)?.max ?: MAX_LONG_SIZE
    field.setLong(obj, Random.nextLong(max) + 1)
}

private fun <T : Any> setLongIdField(obj: T, field: Field) {
    field.setLong(obj, Date().time)
}

private fun <T : Any> setDoubleField(obj: T, field: Field) {
    val max = (field.annotations.find { it is MimicDouble } as? MimicDouble)?.max ?: MAX_DOUBLE_SIZE
    field.setDouble(obj, Random.nextDouble(max) + 1.0)
}

private fun <T : Any> setFloatField(obj: T, field: Field) {
    field.setFloat(obj, Random.nextFloat() + 1.0f)
}

private fun <T : Any> setBooleanField(obj: T, field: Field) {
    field.setBoolean(obj, Random.nextInt(2) == 1)
}

private fun <T : Any> setDateField(obj: T, field: Field) {
    val annotation = field.annotations.find { it is MimicDate } as? MimicDate
    val minDate = annotation?.minTime ?: MIN_TIME
    val maxDate = annotation?.maxTime ?: MAX_TIME
    val time = if (minDate == maxDate) minDate else Random.nextLong(minDate, maxDate)
    field.set(obj, Date(time))
}

private fun <T : Any> setObjectField(obj: T, field: Field, mimicAnnotationOnly: Boolean) {
    val annotation = field.annotations.find { it is MimicObject<*> } as? MimicObject<*> ?: return
    val mimicObj = annotation.clazz.java.generateObj(mimicAnnotationOnly)
    field.set(obj, mimicObj)
}

private fun <T : Any> setListField(obj: T, field: Field, mimicAnnotationOnly: Boolean) {
    val annotation = field.annotations.find { it is MimicList<*> } as? MimicList<*> ?: return
    val mimicObj = annotation.clazz.java.generateList(annotation.size, mimicAnnotationOnly)
    field.set(obj, mimicObj)
}