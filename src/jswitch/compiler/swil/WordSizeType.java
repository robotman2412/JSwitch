package jswitch.compiler.swil;

public enum WordSizeType {
	/** Multiples of 8 bits long. */
	BYTES,
	/** Number of bits long. */
	BITS,
	/** Multiples of the platform word size. */
	SYSTEM_WORDS,
	/** Usually multiples of half the size of the system word. */
	SMALL_SYSTEM_WORDS
}
