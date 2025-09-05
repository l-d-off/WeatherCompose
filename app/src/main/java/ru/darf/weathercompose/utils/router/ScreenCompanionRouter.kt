package ru.darf.weathercompose.utils.router

import androidx.navigation.NavHostController
import ru.darf.weathercompose.utils.ext.getQualifiedName

/** [routeDefault] принимает имя класса, в котором компаньон наследует [ScreenCompanionRouter] */
abstract class ScreenCompanionRouter {

    // Базовый route
    val routeDefault = "${this::class.getQualifiedName}/"

    // Базовый route, который можно превратить в route с параметрами
    open val route = routeDefault

    // Навигация
    open fun navigate(host: NavHostController) = host.navigate(route)

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
