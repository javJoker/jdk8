/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang.annotation;


/**
 * Indicates that a field defining a constant value may be referenced
 * from native code.
 *
 * The annotation may be used as a hint by tools that generate native
 * header files to determine whether a header file is required, and
 * if so, what declarations it should contain.
 *
 * @since 1.8
 */

/**
 * 指可以从本机代码引用定义常量值的字段。
 * 该注解可以用作生成本机头文件的工具的提示，以确定是否需要头文件，
 * 如果需要，它应该包含哪些声明。
 *
 *
 * 一个Native Method就是一个java调用非java代码的接口。该方法的实现由非java语言实现.
 *
 * 标识符native可以与所有其它的java标识符连用，但是abstract除外。这是合理的，
 * 因为native暗示这些方法是有实现体的，只不过这些实现体是非java的，
 * 但是abstract却显然的指明这些方法无实现体。native与其它java标识符连用时，
 * 其意义同非Native Method并无差别。
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Native {
}
