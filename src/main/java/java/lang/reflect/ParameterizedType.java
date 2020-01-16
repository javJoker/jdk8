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

package java.lang.reflect;


/**
 * ParameterizedType represents a parameterized type such as
 * Collection&lt;String&gt;.
 *
 * <p>A parameterized type is created the first time it is needed by a
 * reflective method, as specified in this package. When a
 * parameterized type p is created, the generic type declaration that
 * p instantiates is resolved, and all type arguments of p are created
 * recursively. See {@link java.lang.reflect.TypeVariable
 * TypeVariable} for details on the creation process for type
 * variables. Repeated creation of a parameterized type has no effect.
 *
 * <p>Instances of classes that implement this interface must implement
 * an equals() method that equates any two instances that share the
 * same generic type declaration and have equal type parameters.
 *
 * @since 1.5
 */
/**
 * ParameterizedType表示参数化类型，这个类可以用来检验泛型是否被参数化。
 * 具有<>（泛型）符号的变量是参数化类型。
 *
 * ParameterizedType是在反射方法第一次需要时创建的，如包中指定的。
 * 当创建参数化类型p时，解析p实例化的泛型类型声明，并且递归地创建p的所有类型参数。
 * 重复创建参数化类型无效。
 *
 * 实现此接口的类的实例必须实现一个equals（）方法，该方法使共享相同泛型类型声明并具有相同类型参数的任何两个实例相等。
 *
 * 可以看这篇博客介绍：https://www.jianshu.com/p/27772c32fa41
 * 1.Type 是Java编程语言中所有类型的通用超级接口。这些类型包括原始类型、参数化类型、数组类型、类型变量和基元类型。
 *    内部的 getTypeName() 方法会返回对应类型的全限定类名。
 * 2.只有具有<>（泛型）符号的变量是参数化类型。
 * 3.getActualTypeArguments()返回了一个Type数组,数组里是参数化类型的参数，可以根据这个方法获取到 “泛型的类型”。
 * 4.getRawType()是获取变量的类型。
 * 5.getOwnerType()方法对于O<T>.I<S>类型的变量,会返回O<T>，例如：Map.Entry<Long,Short> mapLong，会返回java.util.Map，而不是java.util.Entry。
 */
public interface ParameterizedType extends Type {
    /**
     * Returns an array of {@code Type} objects representing the actual type
     * arguments to this type.
     *
     * <p>Note that in some cases, the returned array be empty. This can occur
     * if this type represents a non-parameterized type nested within
     * a parameterized type.
     *
     * @return an array of {@code Type} objects representing the actual type
     *     arguments to this type
     * @throws TypeNotPresentException if any of the
     *     actual type arguments refers to a non-existent type declaration   // 如果任何实际类型参数引用了不存在的类型声明
     * @throws MalformedParameterizedTypeException if any of the
     *     actual type parameters refer to a parameterized type that cannot
     *     be instantiated for any reason  // 如果任何实际类型参数引用由于某种原因而无法实例化的参数化类型
     * @since 1.5
     */
    /**
     * 返回了一个Type数组,数组里是参数化类型的参数。
     */
    Type[] getActualTypeArguments();

    /**
     * Returns the {@code Type} object representing the class or interface
     * that declared this type.
     *
     * @return the {@code Type} object representing the class or interface
     *     that declared this type
     * @since 1.5
     */
    /**
     * 返回类或接口的{@code Type}对象的类型
     */
    Type getRawType();

    /**
     * Returns a {@code Type} object representing the type that this type
     * is a member of.  For example, if this type is {@code O<T>.I<S>},
     * return a representation of {@code O<T>}.
     *
     * <p>If this type is a top-level type, {@code null} is returned.
     *
     * @return a {@code Type} object representing the type that
     *     this type is a member of. If this type is a top-level type,
     *     {@code null} is returned
     * @throws TypeNotPresentException if the owner type
     *     refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if the owner type
     *     refers to a parameterized type that cannot be instantiated
     *     for any reason
     * @since 1.5
     */
    /**
     * 返回@code type对象，该对象表示此类型所属的类型。
     * 例如，O<T>.I<S>类型的变量,调用getOwnerType()会返回O<T>.
     *
     * 如果此类型是顶级类型，则返回@code null。
     */
    Type getOwnerType();
}
