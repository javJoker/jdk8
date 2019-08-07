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
 * Indicates that an annotation type is automatically inherited.  If
 * an Inherited meta-annotation is present on an annotation type
 * declaration, and the user queries the annotation type on a class
 * declaration, and the class declaration has no annotation for this type,
 * then the class's superclass will automatically be queried for the
 * annotation type.  This process will be repeated until an annotation for this
 * type is found, or the top of the class hierarchy (Object)
 * is reached.  If no superclass has an annotation for this type, then
 * the query will indicate that the class in question has no such annotation.
 *
 * <p>Note that this meta-annotation type has no effect if the annotated
 * type is used to annotate anything other than a class.  Note also
 * that this meta-annotation only causes annotations to be inherited
 * from superclasses; annotations on implemented interfaces have no
 * effect.
 *
 * @author  Joshua Bloch
 * @since 1.5
 * @jls 9.6.3.3 @Inherited
 */

/**
 * 表示自动继承注释类型。 如果注释类型声明中存在Inherited元注释，
 * 并且用户在类声明上查询注释类型，并且类声明没有此类型的注释，
 * 则将自动查询类的超类以获取注释类型。
 * 将重复此过程，直到找到此类型的注释，或者到达类层次结构（对象）的顶部。
 * 如果没有超类具有此类型的注释，则查询将指示所讨论的类没有此类注释。
 * 请注意，如果注释类型用于注释除类之外的任何其他类型，则此元注释类型无效。
 * 另请注意，此元注释仅导致注释从超类继承; 已实现接口上的注释无效。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Inherited {
}
