package com.milad.hasin_project.data.utils


import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class ResultTest() {
    @Test
    fun test_success_result() {
        val actual = Result.success("Success")
        val expected = Result(Status.SUCCESS, "Success", null)

        assertEquals("test Result", expected, actual)
        assertEquals("test result status", Status.SUCCESS, actual.status)
        assertEquals("test result data", "Success", actual.data)
        assertNull("test result message", actual.message)
    }

    @Test
    fun test_error_result() {
        val actual = Result.error(msg = "error", data = null)
        val expected = Result(Status.ERROR, null, "error")

        assertEquals("test Result", expected, actual)
        assertEquals("test result status", Status.ERROR, actual.status)
        assertNull("test result data", actual.data)
        assertEquals("test result message", "error", actual.message)
    }

    @Test
    fun test_loading_result() {
        val actual = Result.loading(data = null)
        val expected = Result(Status.LOADING, null, null)

        assertEquals("test Result", expected, actual)
        assertEquals("test result status", Status.LOADING, actual.status)
        assertNull("test result data", actual.data)
        assertNull("test result message", actual.message)
    }
}