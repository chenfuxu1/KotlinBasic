package com.chapter07.example.mvvmone



/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:39
 *
 * Desc:
 */
class MainViewModel {
    val databaseModel by modelOf<DatabaseModel>()
    val networkModel by modelOf<NetworkModel>()
}