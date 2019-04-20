package br.bano.mimic.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class MimicObject<T : Any>(val clazz: KClass<T>)