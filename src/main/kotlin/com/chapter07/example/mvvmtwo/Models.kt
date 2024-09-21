package com.chapter07.example.mvvmtwo

import java.util.concurrent.ConcurrentHashMap

object Models {
    private val modelMap = ConcurrentHashMap<String, AbsModel>()

    fun <T : AbsModel> String.get(): T {
        return modelMap[this] as T
    }

    fun AbsModel.register(name: String = this.javaClass.simpleName) {
        modelMap[name] = this
    }
}