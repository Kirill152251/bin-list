package com.bininfo.domain

import javax.inject.Inject

interface InputValidationUseCase {

    enum class InputError {
        NOT_EIGHT_DIGITS,
        INVALID_CHARS,
        EMPTY,
        NO_ERRORS
    }

    companion object {
        const val CORRECT_BIN_LENGTH = 8
    }

    fun isValid(bin: String): InputError

    class Base @Inject constructor() : InputValidationUseCase {
        override fun isValid(bin: String): InputError {
            return when {
                bin.isBlank() -> InputError.EMPTY
                bin.length != CORRECT_BIN_LENGTH -> InputError.NOT_EIGHT_DIGITS
                !bin.all { char -> char.isDigit() } -> InputError.INVALID_CHARS
                else -> InputError.NO_ERRORS
            }
        }
    }
}