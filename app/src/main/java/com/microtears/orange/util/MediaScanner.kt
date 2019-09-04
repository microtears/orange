package com.microtears.orange.util

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri

class MediaScanner(context: Context) : MediaScannerConnection.MediaScannerConnectionClient {
    private val conn: MediaScannerConnection =
        MediaScannerConnection(context.applicationContext, this)
    private val files = mutableListOf<String>()
    private val mimeTypes = mutableListOf<String>()
    private var count = 0

    fun scanFiles(files: List<String>, mimeTypes: List<String>) {
        if (files.size != mimeTypes.size)
            throw IllegalArgumentException("size must equal")
        this.files.addAll(files)
        this.mimeTypes.addAll(mimeTypes)
        conn.connect()
    }

    fun scanFile(file: String, mimeType: String) {
        scanFiles(listOf(file), listOf(mimeType))
    }

    override fun onMediaScannerConnected() {
        files.forEachIndexed { index, path ->
            conn.scanFile(path, mimeTypes[index])
        }
        files.clear()
        mimeTypes.clear()
    }

    override fun onScanCompleted(path: String?, uri: Uri?) {
        count++
        if (count == files.size) {
            conn.disconnect()
            count = 0
        }
    }
}

fun Context.scanFile(file: String, mimeType: String) {
    MediaScanner(this).scanFile(file, mimeType)
}

fun Context.scanFiles(files: List<String>, mimeTypes: List<String>) {
    MediaScanner(this).scanFiles(files, mimeTypes)
}
