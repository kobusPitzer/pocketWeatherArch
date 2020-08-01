package com.kobus.pitzer.updatedweather.repository.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

open class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    companion object {
        private var NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
        private var KEEP_ALIVE_TIME = 1L
        private var KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

        fun createInstance(): AppExecutors {
            return AppExecutors(
                ThreadPoolExecutor(
                    NUMBER_OF_CORES,
                    NUMBER_OF_CORES * 2 + 1,
                    KEEP_ALIVE_TIME,
                    KEEP_ALIVE_TIME_UNIT,
                    LinkedBlockingQueue<Runnable>()
                ),
                Executors.newFixedThreadPool(4),
                MainThreadExecutor()
            )
        }
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}