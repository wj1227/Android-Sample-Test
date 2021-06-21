package com.jay.androidallsampletest.doucmentdownload

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jay.androidallsampletest.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_coordinator.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File


class DoucmentDownloadActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val documentList = listOf(
        "https://developer.android.com/images/jetpack/compose/compose-testing-cheatsheet.pdf",
        "https://developer.android.com/images/training/dependency-injection/hilt-annotations.pdf",
        "https://android.github.io/android-test/downloads/espresso-cheat-sheet-2.1.0.pdf"
    )

    val test = "https://denti-i.kr.object.gov-ncloudstorage.com/upload/documents/1617865473282_S36C.pdf"

    private val btnDownload: Button by lazy {
        findViewById(R.id.btn_download)
    }

    private val btnPermission: Button by lazy {
        findViewById(R.id.btn_permission)
    }

//    private val actionRequestPermission =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//            handlePermissionSectionVisibility()
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doucment_download)

        if (checkPermission()) {
            btnPermission.visibility = View.VISIBLE
            btnDownload.visibility = View.GONE
        } else {
            btnPermission.visibility = View.GONE
            btnDownload.visibility = View.VISIBLE
        }

        btnDownload.setOnClickListener {
            addRandomFile()
        }

        btnPermission.setOnClickListener {
            // permission check
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            true
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun addRandomFile() {
        //val randomRemoteURL = documentList.random()
        val randomRemoteURL = test
        val extension = randomRemoteURL.substring(randomRemoteURL.lastIndexOf(".") + 1)
        val fileName = generateFilename(extension)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val newFileUri = addFileToDownloadsApi29(fileName)
                    val outputStream = contentResolver.openOutputStream(newFileUri, "w")
                        ?: throw Exception("ContentResolver couldn't open $newFileUri outputStream")

                    val responseBody = downloadFileFromInterent(randomRemoteURL) ?: return@launch

                    responseBody.use { body ->
                        outputStream.use { output ->
                            body.byteStream().copyTo(output)
                        }
                    }

                    Log.d(TAG, "File downloaded $newFileUri")

                    val path = getMediaStoreEntryPathApi29(newFileUri)
                        ?: throw Exception("ContentResolver couldn't find $newFileUri")

                    scanFilePath(path, responseBody.contentType().toString()) { uri ->
                        Log.d(TAG, "MediaStore updated: ($path, $uri)")
                        CoroutineScope(Dispatchers.Main).launch {
                            val fileDetails = getFileDetails(uri)
                            Log.d(TAG, "New file: $fileDetails")
                        }
                    }
                } else {
                    val file = addFileToDownloadsApi21(fileName)
                    val outputStream = file.outputStream()

                    val responseBody = downloadFileFromInterent(randomRemoteURL) ?: return@launch

                    // .use is an extension function that closes the output stream where we're
                    // saving the file content once its lambda is finished being executed
                    responseBody.use { body ->
                        outputStream.use { output ->
                            body.byteStream().copyTo(output)
                        }
                    }

                    Log.d(TAG, "21 File downloaded (${file.absolutePath}")

                    // We scan the newly added file to make sure MediaStore.Files is always up to
                    // date
                    scanFilePath(file.path, responseBody.contentType().toString()) { uri ->
                        Log.d(TAG, "21 MediaStore updated (${file.path}, $uri)")

                        CoroutineScope(Dispatchers.Main).launch {
                            val fileDetails = getFileDetails(uri)
                            Log.d(TAG, "21 New file: $fileDetails")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "exception: ${e.printStackTrace()}")
                e.printStackTrace()
            }
        }
    }

    /**
     * Generate random filename when saving a new file
     */
    private fun generateFilename(extension: String): String {
        return "${System.currentTimeMillis()}.$extension"
    }

    /**
     * Create a file inside the Download folder using MediaStore API
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun addFileToDownloadsApi29(fileName: String): Uri {
        val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        return withContext(Dispatchers.IO) {
            val newFile = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            }

            return@withContext contentResolver.insert(collection, newFile)
                ?: throw Exception("MediaStore Uri couldn't be created")
        }
    }

    /**
     * Downloads a random file from interent and saves its content to the specified outputStream
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun downloadFileFromInterent(url: String): ResponseBody? {
        val request = Request.Builder().url(url).build()

        return withContext(Dispatchers.IO) {
            val response = OkHttpClient().newCall(request).execute()
            return@withContext response.body
        }
    }

    /**
     * Get a path for a MediaStore entry as it's needed when calling MediaScanner
     */
    private suspend fun getMediaStoreEntryPathApi29(uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            val cursor = contentResolver.query(
                uri,
                arrayOf(MediaStore.Files.FileColumns.DATA),
                null,
                null,
                null
            ) ?: return@withContext null

            cursor.use {
                if (!cursor.moveToFirst()) {
                    return@withContext null
                }

                return@withContext cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
            }
        }
    }

    /**
     * When adding a file (using java.io or ContentResolver APIs), MediaStore might not be aware of
     * the new entry or doesn't have an updated version of it. That's why some entries have 0 bytes
     * size, even though the file is definitely not empty. MediaStore will eventually scan the file
     * but it's better to do it ourselves to have a fresher state whenever we can
     */
    private suspend fun scanFilePath(
        path: String,
        mimeType: String,
        callback: (uri: Uri) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            MediaScannerConnection.scanFile(
                this@DoucmentDownloadActivity,
                arrayOf(path),
                arrayOf(mimeType)
            ) { _, uri ->
                callback(uri)
            }
        }
    }

    /**
     * Get file details using the MediaStore API
     */
    private suspend fun getFileDetails(uri: Uri): FileEntry? {
        return withContext(Dispatchers.IO) {
            val cursor = contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.SIZE,
                    MediaStore.Files.FileColumns.MIME_TYPE,
                    MediaStore.Files.FileColumns.DATE_ADDED,
                    MediaStore.Files.FileColumns.DATA
                ),
                null,
                null,
                null
            ) ?: return@withContext null

            cursor.use {
                if (!cursor.moveToFirst()) {
                    return@withContext null
                }

                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
                val mimeTypeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
                val dateAddedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

                return@withContext FileEntry(
                    filename = cursor.getString(displayNameColumn),
                    size = cursor.getLong(sizeColumn),
                    mimeType = cursor.getString(mimeTypeColumn),
                    // FileColumns.DATE_ADDED is in seconds, not milliseconds
                    addedAt = cursor.getLong(dateAddedColumn) * 1000,
                    path = cursor.getString(dataColumn),
                )
            }
        }
    }

    /**
     * Create a file inside the Download folder using java.io API
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun addFileToDownloadsApi21(filename: String): File {
        val downloadsFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // Get path of the destination where the file will be saved
        val newNonMediaFile = File(downloadsFolder, filename)

        return withContext(Dispatchers.IO) {
            // Create new file if it does not exist, throw exception otherwise
            if (!newNonMediaFile.createNewFile()) {
                throw Exception("File ${newNonMediaFile.name} already exists")
            }

            return@withContext newNonMediaFile
        }
    }

}

@Parcelize
data class FileEntry(
    val filename: String,
    val size: Long,
    val mimeType: String,
    val addedAt: Long,
    val path: String
) : Parcelable