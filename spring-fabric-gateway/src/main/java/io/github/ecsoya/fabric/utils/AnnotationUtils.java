package io.github.ecsoya.fabric.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>
 * The type Annotation utils.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class AnnotationUtils {

    /**
     * Annotation utils
     */
    private AnnotationUtils() {
	}

    /**
     * Gets annotated element.
     *
     * @param <A>            the type parameter
     * @param clazz          the clazz
     * @param annotationType the annotation type
     * @return the annotated element
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:17
     */
    public static <A extends Annotation> AnnotatedElement getAnnotatedElement(Class<?> clazz, Class<A> annotationType) {
		if (clazz == null || annotationType == null) {
			return null;
		}
		Target target = annotationType.getAnnotation(Target.class);
		if (target == null) {
			return null;
		}
		AnnotatedElement element = null;
		ElementType[] value = target.value();
		for (ElementType elementType : value) {

			switch (elementType) {
			case FIELD:
				element = getField(clazz, annotationType);
				break;
			case METHOD:
				element = getMethod(clazz, annotationType);
				break;
			case TYPE:
				element = getType(clazz, annotationType);
				break;
			default:
				break;
			}
			if (element != null) {
				break;
			}
		}
		return element;
	}

    /**
     * Gets type.
     *
     * @param <A>            the type parameter
     * @param clazz          the clazz
     * @param annotationType the annotation type
     * @return the type
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:17
     */
    public static <A extends Annotation> AnnotatedElement getType(Class<?> clazz, Class<A> annotationType) {
		if (clazz == null || annotationType == null) {
			return null;
		}
		if (clazz.getAnnotation(annotationType) != null) {
			return clazz;
		}
		return null;
	}

    /**
     * Gets field.
     *
     * @param <A>            the type parameter
     * @param clazz          the clazz
     * @param annotationType the annotation type
     * @return the field
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:17
     */
    public static <A extends Annotation> AnnotatedElement getField(Class<?> clazz, Class<A> annotationType) {
		if (clazz == null || annotationType == null) {
			return null;
		}
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if (field.getAnnotation(annotationType) != null) {
				return field;
			}
		}
		return null;
	}

    /**
     * Gets method.
     *
     * @param <A>            the type parameter
     * @param clazz          the clazz
     * @param annotationType the annotation type
     * @return the method
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:17
     */
    public static <A extends Annotation> AnnotatedElement getMethod(Class<?> clazz, Class<A> annotationType) {
		if (clazz == null || annotationType == null) {
			return null;
		}
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(annotationType) != null) {
				return method;
			}
		}
		return null;
	}

    /**
     * Gets value.
     *
     * @param <T>              the type parameter
     * @param object           the object
     * @param annotatedElement the annotated element
     * @param type             the type
     * @return the value
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:17
     */
    @SuppressWarnings("unchecked")
	public static <T> T getValue(Object object, AnnotatedElement annotatedElement, Class<T> type) {
		if (object == null || annotatedElement == null || type == null) {
			return null;
		}
		Object value = null;
		if (annotatedElement instanceof Field) {
			Field f = (Field) annotatedElement;
			f.setAccessible(true);
			try {
				value = f.get(object);
			} catch (Exception e) {
				value = null;
			}
		} else if (annotatedElement instanceof Method) {
			Method m = (Method) annotatedElement;
			m.setAccessible(true);
			try {
				value = m.invoke(object);
			} catch (Exception e) {
				value = null;
			}
		}
		if (value == null) {
			return null;
		} else if (type.isAssignableFrom(value.getClass())) {
			return (T) value;
		} else if (String.class == type) {
			return (T) value.toString();
		}
		return null;
	}

}
