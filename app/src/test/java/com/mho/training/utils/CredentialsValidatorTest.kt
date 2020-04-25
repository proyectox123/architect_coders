package com.mho.training.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CredentialsValidatorTest{

    private val credentialsValidator = CredentialsValidator()

    @Test
    fun `validatePassword should return true when input is not null`(){
        //GIVEN
        val input = "password"

        //THEN
        assertTrue(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validatePassword should return true when input is not empty`(){
        //GIVEN
        val input = "otherpassword"

        //THEN
        assertTrue(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validatePassword should return true when input is longer or equal than eight characters`(){
        //GIVEN
        val input = "12345678"

        //THEN
        assertTrue(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validatePassword should return false when input is null`(){
        //GIVEN
        val input: String? = null

        //THEN
        assertFalse(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validatePassword should return false when input is empty`(){
        //GIVEN
        val input = ""

        //THEN
        assertFalse(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validatePassword should return false when input is lesser than eight characters`(){
        //GIVEN
        val input = "1234567"

        //THEN
        assertFalse(credentialsValidator.validatePassword(input))
    }

    @Test
    fun `validateUsername should return true when input is not null`(){
        //GIVEN
        val input = "coders"

        //THEN
        assertTrue(credentialsValidator.validateUsername(input))
    }

    @Test
    fun `validateUsername should return true when input is not empty`(){
        //GIVEN
        val input = "coders"

        //THEN
        assertTrue(credentialsValidator.validateUsername(input))
    }

    @Test
    fun `validateUsername should return true when input is longer or equal than six characters`(){
        //GIVEN
        val input = "coders"

        //THEN
        assertTrue(credentialsValidator.validateUsername(input))
    }

    @Test
    fun `validateUsername should return false when input is null`(){
        //GIVEN
        val input: String? = null

        //THEN
        assertFalse(credentialsValidator.validateUsername(input))
    }

    @Test
    fun `validateUsername should return false when input is empty`(){
        //GIVEN
        val input = ""

        //THEN
        assertFalse(credentialsValidator.validateUsername(input))
    }

    @Test
    fun `validateUsername should return false when input is lesser than six characters`(){
        //GIVEN
        val input = "coder"

        //THEN
        assertFalse(credentialsValidator.validateUsername(input))
    }

}