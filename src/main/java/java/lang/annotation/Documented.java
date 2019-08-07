/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Indicates that annotations with a type are to be documented by javadoc
 * and similar tools by default.  This type should be used to annotate the
 * declarations of types whose annotations affect the use of annotated
 * elements by their clients.  If a type declaration is annotated with
 * Documented, its annotations become part of the public API
 * of the annotated elements.
 *
 * @author  Joshua Bloch
 * @since 1.5
 */

/**
 * 表示默认情况下，javadoc和类似工具将记录带有类型的注解。
 * 此类型应用于注解类型的声明，其注解会影响客户端对带注解元素的使用。
 * 如果使用Documented注解类型声明，则其注解将成为带注解元素的公共API的一部分。
 *
 * 1.RetentionPolicy 枚举类型
 * 注释保留策略。此枚举类型的常量描述了用于保留注释的各种策略。
 * 它们与{@link Retention}元注释类型一起使用，以指定要保留注释的时间。
 *
 * (1)SOURCE  ---- 注解不进行编译
 * (2)CLASS   ---- 注释将由编译器记录在类文件中，但在运行时不需要由VM保留。这是默认行为。
 * (3)RUNTIME ---- 注释将由编译器记录在类文件中，并在运行时由VM保留，因此可以反射性地读取它们。
 *
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Documented {
}
