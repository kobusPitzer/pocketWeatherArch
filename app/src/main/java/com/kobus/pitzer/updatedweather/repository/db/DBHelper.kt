package com.kobus.pitzer.updatedweather.repository.db

import com.commonsware.cwac.saferoom.SafeHelperFactory
import com.kobus.pitzer.updatedweather.BuildConfig
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.util.*


class DBHelper {
    companion object {
        fun getPassPhrase(): SafeHelperFactory? {
            if (BuildConfig.DEBUG) {
                return null
            }
            return SafeHelperFactory(toBytes("SomeRandomString".toCharArray()))
        }

        fun toBytes(chars: CharArray): ByteArray {
            val charBuffer = CharBuffer.wrap(chars)
            val byteBuffer = Charset.forName("UTF-8").encode(charBuffer)
            val bytes = Arrays.copyOfRange(
                byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit()
            )
            Arrays.fill(byteBuffer.array(), 0.toByte()) // clear sensitive data
            return bytes
        }
    }
}