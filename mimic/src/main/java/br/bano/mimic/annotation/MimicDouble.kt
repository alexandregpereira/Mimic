package br.bano.mimic.annotation

import br.bano.mimic.MAX_DOUBLE_SIZE

@Target(AnnotationTarget.FIELD)
annotation class MimicDouble(
    val max: Double = MAX_DOUBLE_SIZE
)