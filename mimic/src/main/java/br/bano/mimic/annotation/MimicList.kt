package br.bano.mimic.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class MimicList<T : Any>(val clazz: KClass<T>, val size: Int)