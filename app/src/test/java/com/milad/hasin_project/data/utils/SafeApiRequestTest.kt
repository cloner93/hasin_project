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


class SafeApiRequestTest : SafeApiRequest() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun return_2_emit_after_call() = runTest(testDispatcher) {

        val testData = Response.success("")
        val actual = apiRequest { testData }.count()

        assertEquals("return count",2, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_first_emit_is_loading() = runTest(testDispatcher) {

        val testData = Response.success("")
        val actual = apiRequest { testData }.first()

        assertEquals("return loading", Result.loading(null), actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_success_emit() = runTest(testDispatcher) {

        val testData = Response.success("")
        val actual = apiRequest { testData }.last()

        assertEquals("return success", Result.success(""), actual)
        assertEquals("return success status", Status.SUCCESS, actual.status)
        assertEquals("return success data", "", actual.data)
    }
}