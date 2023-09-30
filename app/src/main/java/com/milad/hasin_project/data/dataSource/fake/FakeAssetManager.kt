package com.milad.hasin_project.data.dataSource.fake

import java.io.InputStream

/**
 * The `FakeAssetManager` interface provides a contract for interacting with fake assets.
 */
interface FakeAssetManager {

    /**
     * Opens an input stream for the specified fake asset file.
     *
     * @param fileName The name of the fake asset file.
     * @return An [InputStream] representing the contents of the fake asset.
     */
    fun open(fileName: String): InputStream
}