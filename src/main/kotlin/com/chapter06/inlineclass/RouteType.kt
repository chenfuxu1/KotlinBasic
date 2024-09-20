package com.chapter06.inlineclass

import com.inlineclass.RouteTypeDef

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-15 11:07
 *
 * 内联类
 **/
enum class RouteTypeEnum {
    WALK,
    BUS,
    CAR
}

fun setRouteType(@RouteTypeDef routeType: Int) {

}

fun setRouteTypeByInLine(routeType: RouteTypeInline) {

}

fun main() {
    // IntDef 在 kotlin 中不生效
    setRouteType(55)

    // 限定了范围
    setRouteTypeByInLine(RouteTypes.BUS)
}