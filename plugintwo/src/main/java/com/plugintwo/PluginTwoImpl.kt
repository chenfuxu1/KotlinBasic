package com.plugintwo

import com.plugin.Plugin


class PluginTwoImpl: Plugin {
    override fun start() {
        println("PluginTwo start...")
        newMethod()
    }

    fun newMethod(){
        println("PluginTwo newMethod called...")
    }

    override fun stop() {
        println("PluginTwo stop...")
    }
}
