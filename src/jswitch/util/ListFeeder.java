package jswitch.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListFeeder<Type> implements Feeder<Type> {
	
	protected List<Type> list;
	protected int index;
	
	public ListFeeder(List<Type> list) {
		this.list = Collections.unmodifiableList(list);
		index = 0;
	}
	
	@Override
	public Type getOne() throws ArrayIndexOutOfBoundsException {
		index++;
		return list.get(index - 1);
	}
	
	@Override
	public Collection<Type> get(int number) throws ArrayIndexOutOfBoundsException {
		List<Type> out = list.subList(index, index + number);
		index += number;
		return out;
	}
	
	@Override
	public int length() {
		return list.size() - index;
	}
	
	@Override
	public int fullLength() {
		return list.size();
	}
	
	@Override
	public boolean reset() {
		index = 0;
		return true;
	}
	
	@Override
	public boolean goBack(int num) {
		index -= num;
		return true;
	}
	
}
