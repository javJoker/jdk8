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
 * The common interface extended by all annotation types.  Note that an
 * interface that manually extends this one does <i>not</i> define
 * an annotation type.  Also note that this interface does not itself
 * define an annotation type.
 *
 * More information about annotation types can be found in section 9.6 of
 * <cite>The Java&trade; Language Specification</cite>.
 *
 * The {@link java.lang.reflect.AnnotatedElement} interface discusses
 * compatibility concerns when evolving an annotation type from being
 * non-repeatable to being repeatable.
 *
 * @author  Josh Bloch
 * @since   1.5
 */
/**
 * 1.所有注解类型扩展的公共接口。 请注意，手动扩展此接口的接口不会定义注解类型，且此接口本身不能定义注解类型。
 *
 * 2.注解：不可重复、可重复问题{@link java.lang.reflect.AnnotatedElement}
 */
public interface Annotation {
    /**
     * Returns true if the specified object represents an annotation
     * that is logically equivalent to this one.  In other words,
     * returns true if the specified object is an instance of the same
     * annotation type as this instance, all of whose members are equal
     * to the corresponding member of this annotation, as defined below:
     * <ul>
     *    <li>Two corresponding primitive typed members whose values are
     *    <tt>x</tt> and <tt>y</tt> are considered equal if <tt>x == y</tt>,
     *    unless their type is <tt>float</tt> or <tt>double</tt>.
     *
     *    <li>Two corresponding <tt>float</tt> members whose values
     *    are <tt>x</tt> and <tt>y</tt> are considered equal if
     *    <tt>Float.valueOf(x).equals(Float.valueOf(y))</tt>.
     *    (Unlike the <tt>==</tt> operator, NaN is considered equal
     *    to itself, and <tt>0.0f</tt> unequal to <tt>-0.0f</tt>.)
     *
     *    <li>Two corresponding <tt>double</tt> members whose values
     *    are <tt>x</tt> and <tt>y</tt> are considered equal if
     *    <tt>Double.valueOf(x).equals(Double.valueOf(y))</tt>.
     *    (Unlike the <tt>==</tt> operator, NaN is considered equal
     *    to itself, and <tt>0.0</tt> unequal to <tt>-0.0</tt>.)
     *
     *    <li>Two corresponding <tt>String</tt>, <tt>Class</tt>, enum, or
     *    annotation typed members whose values are <tt>x</tt> and <tt>y</tt>
     *    are considered equal if <tt>x.equals(y)</tt>.  (Note that this
     *    definition is recursive for annotation typed members.)
     *
     *    <li>Two corresponding array typed members <tt>x</tt> and <tt>y</tt>
     *    are considered equal if <tt>Arrays.equals(x, y)</tt>, for the
     *    appropriate overloading of {@link java.util.Arrays#equals}.
     * </ul>
     *
     * @return true if the specified object represents an annotation
     *     that is logically equivalent to this one, otherwise false
     */
    /**
     * 如果指定的对象表示逻辑上等于此注释的注释，则返回true。
     * 换句话说，如果指定的对象是与此实例相同的注释类型的实例，则返回true，
     * 其所有成员都等于此注释的相应成员，如下所示
     *
     * 1.如果x == y，则值为 x 和y 的两个对应的原始类型成员被认为是相等的，
     * 除非它们的类型是 float 或 double 。
     *
     * ===============
     * private static void testFloatEquals() {
     *         float a1 = 0.0f;
     *         float a2 = -0.0f;
     *         System.out.println("float == : " + (a1 == a2));
     *         System.out.println("float equals : " + (Float.valueOf( a1 ).equals( Float.valueOf( a2 ) )));
     *         System.out.println("a1:" + Float.valueOf( a1 ));
     *         System.out.println("a2:" + Float.valueOf( a2 ));
     *     }
     *
     * 结果：
     * float == : true
     * float equals : false
     * a1:0.0
     * a2:-0.0
     *
     * ===============
     *
     * 2. 如果 Arrays.equals（x，y），两个相应的数组类型成员 x 和 y 被认为是相等的，
     * 用于适当的重载{@link java.util.Arrays＃equals}。
     * ===================
     *    private static void testArraysEquals() {
     *         String[] s1 = new String[]{"1","2"};
     *         String[] s2 = new String[]{"1","2"};
     *         System.out.println( Arrays.equals( s1, s2 ));
     *     }
     *
     *     结果：
     *     true
     * ===================
     *
     */
    boolean equals(Object obj);

    /**
     * Returns the hash code of this annotation, as defined below:
     *
     * <p>The hash code of an annotation is the sum of the hash codes
     * of its members (including those with default values), as defined
     * below:
     *
     * The hash code of an annotation member is (127 times the hash code
     * of the member-name as computed by {@link String#hashCode()}) XOR
     * the hash code of the member-value, as defined below:
     *
     * <p>The hash code of a member-value depends on its type:
     * <ul>
     * <li>The hash code of a primitive value <tt><i>v</i></tt> is equal to
     *     <tt><i>WrapperType</i>.valueOf(<i>v</i>).hashCode()</tt>, where
     *     <tt><i>WrapperType</i></tt> is the wrapper type corresponding
     *     to the primitive type of <tt><i>v</i></tt> ({@link Byte},
     *     {@link Character}, {@link Double}, {@link Float}, {@link Integer},
     *     {@link Long}, {@link Short}, or {@link Boolean}).
     *
     * <li>The hash code of a string, enum, class, or annotation member-value
     I     <tt><i>v</i></tt> is computed as by calling
     *     <tt><i>v</i>.hashCode()</tt>.  (In the case of annotation
     *     member values, this is a recursive definition.)
     *
     * <li>The hash code of an array member-value is computed by calling
     *     the appropriate overloading of
     *     {@link java.util.Arrays#hashCode(long[]) Arrays.hashCode}
     *     on the value.  (There is one overloading for each primitive
     *     type, and one for object reference types.)
     * </ul>
     *
     * @return the hash code of this annotation
     */
    /**
     * 返回注解的哈希码
     *
     * 1.原始值的哈希码是对应于原始类型的包装类型，
     * {@link Character}，{@ link Double}，{@ link Float}，{@ link Integer}，{@link Long}，{@ link Short}或{@link Boolean}）
     *
     * 2.字符串，枚举，类或注释成员值的哈希码通过调用来计算
     *
     */
    int hashCode();

    /**
     * Returns a string representation of this annotation.  The details
     * of the representation are implementation-dependent, but the following
     * may be regarded as typical:
     * <pre>
     *   &#064;com.acme.util.Name(first=Alfred, middle=E., last=Neuman)
     * </pre>
     *
     * @return a string representation of this annotation
     */
    String toString();

    /**
     * Returns the annotation type of this annotation.
     * @return the annotation type of this annotation
     */
    /**
     * 返回注解类型
     */
    Class<? extends Annotation> annotationType();
}
