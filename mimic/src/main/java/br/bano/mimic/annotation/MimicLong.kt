package br.bano.mimic.annotation

@Target(AnnotationTarget.FIELD)
annotation class MimicLong(
    val max: Long
)