package jswitch.compiler.typedef;

public enum SimpleNumberTypeEnum {
	
	INTEGER,
	SHORT_INTEGER,
	LONG_INTEGER,
	BYTE,
	FLOAT,
	DOUBLE,
	
	UNSIGNED_INTEGER(INTEGER),
	UNSIGNED_SHORT_INTEGER(SHORT_INTEGER),
	UNSIGNED_LONG_INTEGER(LONG_INTEGER),
	UNSIGNED_BYTE(BYTE);
	
	public final SimpleNumberTypeEnum signed;
	public final boolean isUnsigned;
	
	SimpleNumberTypeEnum() {
		signed = this;
		isUnsigned = false;
	}
	
	SimpleNumberTypeEnum(SimpleNumberTypeEnum signed) {
		this.signed = signed;
		isUnsigned = true;
	}
	
}
