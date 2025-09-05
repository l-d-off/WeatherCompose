package ru.darf.weathercompose.utils.logger

import android.util.Log
import ru.darf.weathercompose.BuildConfig

private val SHOW_LOG = BuildConfig.DEBUG
private const val mainStr = "__ ${BuildConfig.APPLICATION_ID}"

fun logE(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.e(mainStr, any.toList().toString())
}

fun logD(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.d(mainStr, any.toList().toString())
}

fun logW(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.w(mainStr, any.toList().toString())
}

fun logI(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.i(mainStr, any.toList().toString())
}

fun logV(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.v(mainStr, any.toList().toString())
}
