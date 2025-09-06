package ru.darf.weathercompose.core.router

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.navOptions
import ru.darf.weathercompose.core.ext.getQualifiedName

/** [routeDefault] принимает имя класса, в котором компаньон наследует [ScreenCompanionRouter] */
abstract class ScreenCompanionRouter {

    // Базовый route
    val routeDefault = "${this::class.getQualifiedName}/"

    // Базовый route, который можно превратить в route с параметрами
    open val route = routeDefault

    // Навигация
    open fun navigate(
        host: NavHostController,
        builder: NavOptionsBuilder.() -> Unit = {},
    ) = host.navigate(
        route = route,
        builder = builder
    )

    // Получаем route с параметрами
    fun getRouteWithParams(vararg params: String): String =
        routeDefault.addRouteParams(*params)

    // Получаем route с аргументами
    fun getRouteWithArgs(vararg args: Pair<String, String>): String =
        routeDefault.addRouteArgs(*args)
}

// Строка route с параметрами
private fun String.addRouteParams(vararg args: String) = if (args.isEmpty()) {
    this
} else {
    val paramsString = args.joinToString(prefix = "?", separator = ",") { "$it={$it}" }
    "$this$paramsString"
}

// Строка route с аргументами
private fun String.addRouteArgs(vararg args: Pair<String, String>) = if (args.isEmpty()) {
    this
} else {
    val argsString = args.joinToString(prefix = "?", separator = ",") {
        "${it.first}=${it.second}"
    }
    "$this$argsString"
}
