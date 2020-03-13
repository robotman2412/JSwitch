package jswitch.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayFeeder<T> implements Feeder<T> {

	protected T[] array;
	protected int index;
	protected Class<?> arrayType;

	protected ArrayFeeder() {

	}

	public ArrayFeeder(T[] mArray) {
		array = mArray.clone();
		arrayType = mArray.getClass().getComponentType();
	}

	@Override
	public T getOne() throws ArrayIndexOutOfBoundsException {
		T t = array[index];
		index ++;
		return t;
	}

	@Override
	public boolean goBack(int num) {
		if (index < num) {
			return false;
		}
		index -= num;
		return true;
	}

	@Override
	public List<T> get(int number) throws ArrayIndexOutOfBoundsException {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			list.add(array[index + i]);
		}
		index += number;
		return list;
	}

	@Override
	public int length() {
		return array.length - index;
	}

	@Override
	public int fullLength() {
		return array.length;
	}

	@Override
	public boolean reset() {
		index = 0;
		return true;
	}

	@SuppressWarnings("unchecked")
	public T[] getRemaining() {
		int length = length();
		T[] out = (T[]) Array.newInstance(arrayType, length);
		List<T> get = get(length);
		for (int i = 0; i < out.length; i++) {
			out[i] = get.get(i);
		}
		return out;
	}

}
