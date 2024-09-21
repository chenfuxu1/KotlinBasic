package com.chapter07.example.mvvmone

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

object Models {
    private val modelMap = ConcurrentHashMap<Class<out AbsModel>, AbsModel>()

    fun <T : AbsModel> KClass<T>.get(): T {
        return modelMap[this.java] as T
    }

    fun AbsModel.register() {
        modelMap[this.javaClass] = this
    }
}