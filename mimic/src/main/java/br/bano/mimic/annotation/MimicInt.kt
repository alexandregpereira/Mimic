package br.bano.mimic.annotation

import br.bano.mimic.MAX_INT_SIZE

@Target(AnnotationTarget.FIELD)
annotation class MimicInt(
    val max: Int = MAX_INT_SIZE
)