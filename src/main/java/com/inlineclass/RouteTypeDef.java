package com.inlineclass;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-15 11:24
 **/
@Retention(RetentionPolicy.SOURCE)
@IntDef({RouteTypeInt.WALK, RouteTypeInt.BUS, RouteTypeInt.CAR})
public @interface RouteTypeDef {}
