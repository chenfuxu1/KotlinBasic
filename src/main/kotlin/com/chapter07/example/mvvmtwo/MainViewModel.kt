package com.chapter07.example.mvvmtwo

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:39
 *
 * Desc:
 */
class MainViewModel {
    val databaseModel: DatabaseModel by ModelDelegate
    val networkModel: NetworkModel by ModelDelegate
    val spModel: SpModel by ModelDelegate
    val spModel2: SpModel by ModelDelegate
}