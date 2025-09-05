package ru.darf.weathercompose.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
inline fun <T> rememberState(
    crossinline producer: @DisallowComposableCalls () -> T,
) =
    remember { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(
    key1: Any?,
    crossinline producer: @DisallowComposableCalls () -> T,
) =
    remember(key1) { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(
    key1: Any?,
    key2: Any?,
    crossinline producer: @DisallowComposableCalls () -> T,
) =
    remember(key1, key2) { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline producer: @DisallowComposableCalls () -> T,
) =
    remember(key1, key2, key3) { mutableStateOf(producer()) }

@Composable
fun LifecycleEvent(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onAny: () -> Unit = {},
) {
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> onAny()
        }
    }
}

@Composable
private fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {

    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
