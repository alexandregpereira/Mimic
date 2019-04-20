package br.bano.mimic.annotation

import br.bano.mimic.MAX_STRING_LENGTH
import br.bano.mimic.MAX_WORDS
import br.bano.mimic.MIN_WORDS

@Target(AnnotationTarget.FIELD)
annotation class MimicString(
    val maxLength: Int = MAX_STRING_LENGTH,
    val minWords: Int = MIN_WORDS,
    val maxWords: Int = MAX_WORDS
)