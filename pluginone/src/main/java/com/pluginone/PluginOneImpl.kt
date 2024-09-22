package com.pluginone

import com.plugin.Plugin


class PluginOneImpl: Plugin {
    override fun start() {
        println("PluginOne start...")
        newMethod()
    }

    fun newMethod(){
        println("PluginOne newMethod called...")
    }

    override fun stop() {
        println("PluginOne stop...")
    }
}
