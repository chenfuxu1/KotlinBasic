package com.chapter07.example.mvvmone


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:25
 *
 * Desc:
 */
val x: Int
    get() {
        return Math.random().toInt()
    }

fun initModels() {
    DatabaseModel()
    NetworkModel()
}

fun main() {
    initModels()
    val mainViewModel = MainViewModel()
    mainViewModel.databaseModel.query("select * from mysql.user").let(::println)
    mainViewModel.networkModel.get("https://www.imooc.com").let(::println)
}