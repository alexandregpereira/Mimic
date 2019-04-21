package br.bano.mimic.annotation

@Target(AnnotationTarget.FIELD)
annotation class MimicDate(
    val minTime: Long,
    val maxTime: Long
)