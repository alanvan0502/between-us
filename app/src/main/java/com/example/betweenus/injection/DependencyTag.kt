package com.example.betweenus.injection

import org.koin.core.qualifier.named

object DependencyTag {
    val DISPATCHERS_IO = named("DISPATCHER_IO")
    val DISPATCHERS_MAIN = named("DISPATCHERS_MAIN")
    val DISPATCHERS_DEFAULT = named("DISPATCHERS_DEFAULT")
}