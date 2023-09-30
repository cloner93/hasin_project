package com.milad.hasin_project.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom test runner for Hilt-enabled AndroidJUnit tests.
 *
 * This [HiltTestRunner] extends [AndroidJUnitRunner] and overrides [newApplication] to use
 * [HiltTestApplication] as the test application class.
 *
 * To use this runner, add it to the Gradle configuration.
 *
 * Example Gradle configuration:
 *
 * ```
 * android {
 *     defaultConfig {
 *         testInstrumentationRunner "your.package.name.HiltTestRunner"
 *     }
 * }
 * ```
 *
 * @see AndroidJUnitRunner
 * @see HiltTestApplication
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}