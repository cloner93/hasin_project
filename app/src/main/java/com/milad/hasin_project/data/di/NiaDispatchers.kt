package com.milad.hasin_project.data.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * The `Dispatcher` annotation class is a custom qualifier annotation used to specify the dispatcher
 * type for coroutine operations.
 *
 * @param niaDispatcher The enum value representing the dispatcher type.
 *
 * @see Qualifier
 * @see Retention
 */
@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: TmdbDispatchers)

/**
 * The `TmdbDispatchers` enum class defines the available dispatcher types for coroutine operations.
 */
enum class TmdbDispatchers {
    IO,
}
