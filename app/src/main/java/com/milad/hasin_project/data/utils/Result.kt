package com.milad.hasin_project.data.utils

/**
 * The `Result` data class represents the result of an operation, including its status, data, and an optional message.
 *
 * @param T The type of data associated with the result.
 * @property status The status of the result (SUCCESS, ERROR, or LOADING).
 * @property data The actual data associated with the result.
 * @property message An optional message describing the result.
 */
data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        /**
         * Creates a success result with the given data.
         *
         * @param data The data associated with the success result.
         * @return A success [Result] with the provided data.
         */
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        /**
         * Creates an error result with the given message and data.
         *
         * @param msg The error message describing the result.
         * @param data The data associated with the error result.
         * @return An error [Result] with the provided message and data.
         */
        fun <T> error(msg: String, data: T?): Result<T> {
            return Result(Status.ERROR, data, msg)
        }

        /**
         * Creates a loading result with the given data.
         *
         * @param data The data associated with the loading result.
         * @return A loading [Result] with the provided data.
         */
        fun <T> loading(data: T?): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}