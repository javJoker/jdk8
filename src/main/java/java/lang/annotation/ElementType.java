/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The constants of this enumerated type provide a simple classification of the
 * syntactic locations where annotations may appear in a Java program. These
 * constants are used in {@link Target java.lang.annotation.Target}
 * meta-annotations to specify where it is legal to write annotations of a
 * given type.
 *
 * <p>The syntactic locations where annotations may appear are split into
 * <em>declaration contexts</em> , where annotations apply to declarations, and
 * <em>type contexts</em> , where annotations apply to types used in
 * declarations and expressions.
 *
 * <p>The constants {@link #ANNOTATION_TYPE} , {@link #CONSTRUCTOR} , {@link
 * #FIELD} , {@link #LOCAL_VARIABLE} , {@link #METHOD} , {@link #PACKAGE} ,
 * {@link #PARAMETER} , {@link #TYPE} , and {@link #TYPE_PARAMETER} correspond
 * to the declaration contexts in JLS 9.6.4.1.
 *
 * <p>For example, an annotation whose type is meta-annotated with
 * {@code @Target(ElementType.FIELD)} may only be written as a modifier for a
 * field declaration.
 *
 * <p>The constant {@link #TYPE_USE} corresponds to the 15 type contexts in JLS
 * 4.11, as well as to two declaration contexts: type declarations (including
 * annotation type declarations) and type parameter declarations.
 *
 * <p>For example, an annotation whose type is meta-annotated with
 * {@code @Target(ElementType.TYPE_USE)} may be written on the type of a field
 * (or within the type of the field, if it is a nested, parameterized, or array
 * type), and may also appear as a modifier for, say, a class declaration.
 *
 * <p>The {@code TYPE_USE} constant includes type declarations and type
 * parameter declarations as a convenience for designers of type checkers which
 * give semantics to annotation types. For example, if the annotation type
 * {@code NonNull} is meta-annotated with
 * {@code @Target(ElementType.TYPE_USE)}, then {@code @NonNull}
 * {@code class C {...}} could be treated by a type checker as indicating that
 * all variables of class {@code C} are non-null, while still allowing
 * variables of other classes to be non-null or not non-null based on whether
 * {@code @NonNull} appears at the variable's declaration.
 *
 * @author  Joshua Bloch
 * @since 1.5
 * @jls 9.6.4.1 @Target
 * @jls 4.1 The Kinds of Types and Values
 */
/**
 * 此枚举类型的常量提供了注解可能出现在Java程序中的语法位置的简单分类。
 * 这些常量用于{@link Target java.lang.annotation.Target}元注解，以指定编写给定类型注解的合法位置。
 */
public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration */
    // 类、接口（包括注解类型）、枚举
    TYPE,

    /** Field declaration (includes enum constants) */
    // 属性（包括枚举常量）
    FIELD,

    /** Method declaration */
    // 方法
    METHOD,

    /** Formal parameter declaration */
    // 参数
    PARAMETER,

    /** Constructor declaration */
    // 构造函数
    CONSTRUCTOR,

    /** Local variable declaration */
    // 局部变量
    LOCAL_VARIABLE,

    /** Annotation type declaration */
    // 注解类型
    ANNOTATION_TYPE,

    /** Package declaration */
    // 包
    PACKAGE,

    /**
     * Type parameter declaration
     *
     * @since 1.8
     */
    // 参数声明
    TYPE_PARAMETER,

    /**
     * Use of a type
     *
     * @since 1.8
     */
    // 类型声明（包括注解类型声明）和类型参数声明。
    // 可以写在字段的类型上（或者在字段的类型内，如果它是嵌套的，参数化或数组类型），也可以作为类声明的修饰符出现
    TYPE_USE
}
