package jswitch.util;

import java.util.Collection;
import java.util.Iterator;

public interface Feeder<T> extends Iterable<T>, Iterator<T> {

	/**
	 * Gets a single instance from the stream.
	 * @return one instance of <code>T</code>
	 * @throws ArrayIndexOutOfBoundsException when there is no data left
	 */
	T getOne() throws ArrayIndexOutOfBoundsException;

	/**
	 * Gets a number of instances from the stream.
	 * @param number the number of instances to retrieve
	 * @return the collection of the instances retrieved.
	 * @throws ArrayIndexOutOfBoundsException when there is no data left
	 */
	Collection<T> get(int number) throws ArrayIndexOutOfBoundsException;

	/**
	 * @return the remaining length of the stream
	 */
	int length();

	/**
	 * @return the full length of the stream, disregarding position in stream
	 */
	int fullLength();

	/**
	 * Resets to the beginning of the stream.
	 * @return whether or not the stream was reset
	 */
	boolean reset();

	default boolean goBackOne() {
		return goBack(1);
	}

	/**
	 * Goes back <code>num</code> in the feed
	 * @param num the amount of objects to go back
	 * @return whether or not the stream went back
	 */
	boolean goBack(int num);

	/**
	 * Returns iterator as specified by Iterable.
	 * Eats up the entire feed by default.
	 * @return the iterator required by Iterable
	 */
	@Override
	default Iterator<T> iterator() {
		return this;
	}

	@Override
	default boolean hasNext() {
		return length() > 0;
	}

	@Override
	default T next() {
		return getOne();
	}
}
