package ru.darf.weathercompose.core.ext

import java.util.UUID
import kotlin.jvm.internal.ClassBasedDeclarationContainer
import kotlin.reflect.KClass

val <T : Any> KClass<T>.getQualifiedName: String
    @JvmName("getJavaClass")
    get() = ((this as ClassBasedDeclarationContainer).jClass)
        .enclosingClass
        ?.kotlin
        ?.qualifiedName
        ?: UUID.randomUUID().toString()
