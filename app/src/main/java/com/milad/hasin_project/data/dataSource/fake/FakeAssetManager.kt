package com.milad.hasin_project.data.dataSource.fake

import java.io.InputStream

interface FakeAssetManager {
    fun open(fileName: String): InputStream
}