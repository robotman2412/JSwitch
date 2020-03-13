package jswitch.util;

import java.lang.reflect.Array;
import java.lang.reflect.TypeVariable;

public class GenericsHelper<Type> {

	public final Class<? extends Type> clazz;

	public static Class<?>[] getGenerics(Object toGet) {
		return getGenerics(toGet.getClass());
	}

	public static Class<?>[] getGenerics(Class<?> toGet) {
		TypeVariable<? extends Class<?>>[] typeVariables = toGet.getTypeParameters();
		Class<?>[] types = new Class[typeVariables.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = typeVariables[i].getGenericDeclaration();
		}
		return types;
	}

	public GenericsHelper(Class<? extends Type> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Equal to <code>someObject instanceof SomeClass</code> in every way.
	 * @param toCheck the object to check
	 * @return whether or not casting to <code>Type</code> is possible
	 */
	public boolean instanceOfType(Object toCheck) {
		return clazz.isInstance(toCheck);
	}

	@SuppressWarnings("unchecked")
	public Type[] newArray(int length) {
		return (Type[]) Array.newInstance(clazz, length);
	}

	public Object newArray(int... dimensions) {
		return Array.newInstance(clazz, dimensions);
	}

}
