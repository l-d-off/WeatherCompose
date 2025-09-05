package ru.darf.weathercompose.core.logger

import android.util.Log
import ru.darf.weathercompose.BuildConfig

private const val LOG_TAG = "__ ${BuildConfig.APPLICATION_ID}"

private fun runIfNotDebug(action: () -> Unit) {
    if (BuildConfig.DEBUG) {
        action()
    }
}

fun logE(vararg any: Any?) {
    runIfNotDebug {
        Log.e(LOG_TAG, any.toList().toString())
    }
}

fun logD(vararg any: Any?) {
    runIfNotDebug {
        Log.d(LOG_TAG, any.toList().toString())
    }
}

fun logW(vararg any: Any?) {
    runIfNotDebug {
        Log.w(LOG_TAG, any.toList().toString())
    }
}

fun logI(vararg any: Any?) {
    runIfNotDebug {
        Log.i(LOG_TAG, any.toList().toString())
    }
}

fun logV(vararg any: Any?) {
    runIfNotDebug {
        Log.v(LOG_TAG, any.toList().toString())
    }
}
