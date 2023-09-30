package com.milad.hasin_project.data.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response


/**
 * Unit test for the [SafeApiRequest] class.
 */
class SafeApiRequestTest : SafeApiRequest() {

    // Dispatcher for testing
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    // Test case for returning 2 emits after the call
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun return_2_emit_after_call() = runTest(testDispatcher) {

        // Create a test response
        val testData = Response.success("")

        // Get the actual result count
        val actual = apiRequest { testData }.count()

        assertEquals("return count", 2, actual)
    }

    // Test case for the first emit being a loading state
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_first_emit_is_loading() = runTest(testDispatcher) {

        // Create a test response
        val testData = Response.success("")

        // Get the actual result for the first emit
        val actual = apiRequest { testData }.first()

        assertEquals("return loading", Result.loading(null), actual)
    }

    // Test case for a successful emit
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_success_emit() = runTest(testDispatcher) {

        // Create a test response
        val testData = Response.success("")

        // Get the actual result for the last emit
        val actual = apiRequest { testData }.last()

        assertEquals("return success", Result.success(""), actual)
        assertEquals("return success status", Status.SUCCESS, actual.status)
        assertEquals("return success data", "", actual.data)
    }
}