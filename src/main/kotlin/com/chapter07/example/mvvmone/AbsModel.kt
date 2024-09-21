package com.chapter07.example.mvvmone


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:20
 *
 * Desc:
 */
abstract class AbsModel {
    init {
        Models.run {
            this@AbsModel.register()
        }
    }
}