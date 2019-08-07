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
 * Annotation retention policy.  The constants of this enumerated type
 * describe the various policies for retaining annotations.  They are used
 * in conjunction with the {@link Retention} meta-annotation type to specify
 * how long annotations are to be retained.
 *
 * @author  Joshua Bloch
 * @since 1.5
 */

/**
 * 注释保留策略。此枚举类型的常量描述了用于保留注释的各种策略。
 * 它们与{@link Retention}元注释类型一起使用，以指定要保留注释的时间。
 */
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     */
    // 注解不进行编译
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     */
    // 注释将由编译器记录在类文件中，但在运行时不需要由VM保留。这是默认行为。
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    // 注释将由编译器记录在类文件中，并在运行时由VM保留，因此可以反射性地读取它们。
    RUNTIME
}
