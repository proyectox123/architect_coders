package com.mho.training.utils

class CredentialsValidator {

    fun validateUsername(username: String?) =
        (username?.length ?: 0) >= MAX_LENGTH_USERNAME

    fun validatePassword(password: String?) =
        (password?.length ?: 0) >= MAX_LENGTH_PASSWORD

    companion object {
        private const val MAX_LENGTH_PASSWORD: Int = 8
        private const val MAX_LENGTH_USERNAME: Int = 6
    }

}
