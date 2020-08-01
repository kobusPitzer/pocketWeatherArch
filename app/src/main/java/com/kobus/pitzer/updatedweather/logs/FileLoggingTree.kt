package com.kobus.pitzer.updatedweather.logs

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.NonNull
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileLoggingTree(
    @NonNull private var logsDirectory: File,
    private val fileHeader: String?
) : Timber.DebugTree() {

    private val baseFilename: String = "LOG-"

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {

            val fileNameTimeStamp =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val logTimeStamp =
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS", Locale.getDefault()).format(Date())

            val dir = File(logsDirectory.toString())
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val file = File(
                logsDirectory.toString()
                        + File.separator
                        + baseFilename
                        + fileNameTimeStamp
            )

            if (!file.exists()) {
                file.createNewFile()
            }

            if (file.exists()) {
                val fileOutputStream = FileOutputStream(file, true)
                if (file.length() < 1 && fileHeader != null) {
                    fileOutputStream.write(fileHeader.toByteArray())
                    fileOutputStream.write("\n\n".toByteArray())
                }

                fileOutputStream.write(logTimeStamp.toByteArray())
                fileOutputStream.write("\t".toByteArray())
                when (priority) {
                    Log.VERBOSE -> fileOutputStream.write("verbose".toByteArray())
                    Log.DEBUG -> fileOutputStream.write("debug".toByteArray())
                    Log.INFO -> fileOutputStream.write("info".toByteArray())
                    Log.WARN -> fileOutputStream.write("warning".toByteArray())
                    Log.ERROR -> fileOutputStream.write("error".toByteArray())
                }
                fileOutputStream.write("\t".toByteArray())
                if (tag != null) {
                    fileOutputStream.write(tag.toByteArray())
                }
                fileOutputStream.write("\t".toByteArray())
                fileOutputStream.write(message.toByteArray())
                fileOutputStream.write("\n".toByteArray())
                fileOutputStream.close()
            }
        } catch (ex: Exception) {
            Log.e("FileLoggingTree", ex.message)
        }
    }
}