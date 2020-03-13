package jswitch.util;

public class StringFeeder extends ArrayFeeder<Character> {

	public StringFeeder(String input) {
		super();
		char[] chars = input.toCharArray();
		array = new Character[chars.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = chars[i];
		}
	}

	public String getString(int length) throws ArrayIndexOutOfBoundsException {
		char[] c = new char[length];
		Character[] chars = get(length).toArray(new Character[0]);
		for (int i = 0; i < c.length; i++) {
			c[i] = chars[i];
		}
		return new String(c);
	}

}
