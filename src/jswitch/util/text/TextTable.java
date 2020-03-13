package jswitch.util.text;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextTable {

	List<String[]> raw;
	List<Integer> maxLen;

	public TextTable() {
		raw = new ArrayList<String[]>();
		maxLen = new ArrayList<Integer>();
	}

	public void addSimple(Object[] objs) {
		List<Object> arr = new ArrayList<Object>();
		for (Object o : objs) {
			arr.add(o);
		}
		addSimple(arr);
	}

	/**
	 * Simple method used to add an array of objects to the table.
	 * @param objs anything iterable, e.g. an array, Set, Collection or List
	 */
	public void addSimple(Iterable<?> objs) {
		Iterator<?> it = objs.iterator();
		int i = 0;
		List<String> arr = new ArrayList<String>();
		while (it.hasNext()) {
			String s = it.next().toString();
			if (i > maxLen.size() - 1) {
				maxLen.add(s.length());
			}
			else if (s.length() > maxLen.get(i)) {
				maxLen.set(i, s.length());
			}
			arr.add(s);
			i ++;
		}
		raw.add(arr.toArray(new String[0]));
	}

	/**
	 * Used variable argument version of addSimple, easier for anything with always the same amount of arguments.
	 * @param args the objects to add to the table
	 */
	public void add(Object... args) {
		addSimple(args);
	}

	/**
	 * Formats the table with <code>spacing</code> spaces between colomns.
	 * @param spacing the number of spaces to add between colomns
	 * @return the formatted text
	 */
	public String[] format(int spacing) {
		String[] s = new String[raw.size()];
		for (int i = 0; i < raw.size(); i++) {
			s[i] = "";
			for (int l = 0; l < raw.get(i).length; l++) {
				s[i] += raw.get(i)[l];
				if (l < raw.get(i).length - 1) {
					for (int m = 0; m < maxLen.get(l) - raw.get(i)[l].length() + spacing; m++) {
						s[i] += " ";
					}
				}
			}
		}
		return s;
	}

	/**
	 * Formats and prints directly to <code>System.out</code>
	 * @param spacing the number of spaces to add between colomns
	 */
	public void print(int spacing) {
		for (String s : format(spacing)) {
			System.out.println(s);
		}
	}

	/**
	 * Formats and prints directly to <code>out</code>
	 * @param spacing the number of spaces to add between colomns
	 */
	public void print(PrintStream out, int spacing) {
		for (String s : format(spacing)) {
			out.println(s);
		}
	}

}
