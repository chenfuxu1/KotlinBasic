package com.plugin

interface Plugin {
    companion object {
        const val CONFIG = "plugin.config"
        const val KEY = "plugin.impl"
    }

    fun start()

    fun stop()
}