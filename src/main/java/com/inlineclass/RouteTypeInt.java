package com.inlineclass;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-15 11:23
 **/
public class RouteTypeInt {
    public static final int WALK = 0;
    public static final int BUS = 1;
    public static final int CAR = 2;

    public static void main(String[] args) {
        RouteTypeInt.setRouteType(RouteTypeInt.BUS);

        RouteTypeInt.setRouteType(11);
    }

    public static void setRouteType(@RouteTypeDef int routeType) {
        System.out.println(routeType);
    }
}
