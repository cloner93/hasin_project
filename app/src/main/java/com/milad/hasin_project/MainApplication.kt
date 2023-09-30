package com.milad.hasin_project

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The `MainApplication` class serves as the entry point for Hilt's dependency injection.
 * Annotated with `@HiltAndroidApp` and extending the `Application` class.
 */
@HiltAndroidApp
class MainApplication : Application()