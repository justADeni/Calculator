package com.github.justadeni

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform