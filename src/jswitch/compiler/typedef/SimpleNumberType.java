package jswitch.compiler.typedef;

import jswitch.compiler.swil.SWILFragment;
import jswitch.compiler.swil.SWILUtils;

public abstract class SimpleNumberType extends TypeDef implements SWILUtils {
	
	protected final String[] typeNames;
	public final boolean isUnsigned;
	public final SimpleNumberTypeEnum typeEnum;
	
	protected SimpleNumberType(boolean isUnsigned, SimpleNumberTypeEnum typeEnum, String... typeNames) {
		this.isUnsigned = isUnsigned;
		this.typeEnum = typeEnum;
		this.typeNames = typeNames;
	}
	
	public static SimpleNumberType getSignificantType(TypeDef typeLeft, TypeDef typeRight) {
		if (!(typeLeft instanceof SimpleNumberType && typeRight instanceof SimpleNumberType)) {
			return null;
		}
		if (typeLeft == Double.SIGNED || typeRight == Double.SIGNED) {
			return Double.SIGNED;
		}
		else if (typeLeft == Float.SIGNED || typeRight == Float.SIGNED) {
			return Float.SIGNED;
		}
		boolean unsigned = ((SimpleNumberType) typeLeft).isUnsigned && ((SimpleNumberType) typeRight).isUnsigned;
		if (typeLeft == Long.SIGNED || typeRight == Long.SIGNED || typeLeft == Long.UNSIGNED || typeRight == Long.UNSIGNED) {
			return unsigned ? Long.UNSIGNED : Long.SIGNED;
		}
		if (typeLeft == Integer.SIGNED || typeRight == Integer.SIGNED || typeLeft == Integer.UNSIGNED || typeRight == Integer.UNSIGNED) {
			return unsigned ? Integer.UNSIGNED : Integer.SIGNED;
		}
		if (typeLeft == Short.SIGNED || typeRight == Short.SIGNED || typeLeft == Short.UNSIGNED || typeRight == Short.UNSIGNED) {
			return unsigned ? Short.UNSIGNED : Short.SIGNED;
		}
		if (typeLeft == Byte.SIGNED || typeRight == Byte.SIGNED || typeLeft == Byte.UNSIGNED || typeRight == Byte.UNSIGNED) {
			return unsigned ? Byte.UNSIGNED : Byte.SIGNED;
		}
		return null;
	}
	
	@Override
	public String[] getTypeNames() {
		return typeNames;
	}
	
	@Override
	public boolean mayCastTo(TypeDef other) {
		return other instanceof SimpleNumberType;
	}
	
	@Override
	public boolean isSimpleType() {
		return true;
	}
	
	public static class Byte extends SimpleNumberType {
		
		public static final Byte UNSIGNED = new Byte(true);
		public static final Byte SIGNED = new Byte(false);
		
		private Byte(boolean isUnsigned) {
			super(isUnsigned, isUnsigned ? SimpleNumberTypeEnum.UNSIGNED_BYTE : SimpleNumberTypeEnum.BYTE, "byte", "int8_t");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			boolean doUnsigned = t.isUnsigned || isUnsigned;
			switch (t.signed) {
				case INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CUBTOI : IS.CBTOI);
				case SHORT_INTEGER:
					return doUnsigned ? SWILFragment.simple(IS.CUBTOI, IS.CUITOS) : SWILFragment.simple(IS.CBTOI, IS.CITOS);
				case LONG_INTEGER:
					return doUnsigned ? SWILFragment.simple(IS.CUBTOI, IS.CUITOL) : SWILFragment.simple(IS.CBTOI, IS.CITOL);
				case FLOAT:
					return doUnsigned ? SWILFragment.simple(IS.CUBTOI, IS.CUITOF) : SWILFragment.simple(IS.CBTOI, IS.CITOF);
				case DOUBLE:
					return doUnsigned ? SWILFragment.simple(IS.CUBTOI, IS.CUITOD) : SWILFragment.simple(IS.CBTOI, IS.CITOD);
			}
			return null;
		}
		
	}
	
	public static class Integer extends SimpleNumberType {
		
		public static final Integer UNSIGNED = new Integer(true);
		public static final Integer SIGNED = new Integer(false);
		
		private Integer(boolean isUnsigned) {
			super(isUnsigned, isUnsigned ? SimpleNumberTypeEnum.UNSIGNED_INTEGER : SimpleNumberTypeEnum.INTEGER, "int");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			boolean doUnsigned = t.isUnsigned || isUnsigned;
			switch (t.signed) {
				case BYTE:
					return SWILFragment.simple(doUnsigned ? IS.CUITOB : IS.CITOB);
				case SHORT_INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CUITOS : IS.CITOS);
				case LONG_INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CUITOL : IS.CITOL);
				case FLOAT:
					return SWILFragment.simple(doUnsigned ? IS.CUITOF : IS.CITOF);
				case DOUBLE:
					return SWILFragment.simple(doUnsigned ? IS.CUITOD : IS.CITOD);
			}
			return null;
		}
		
	}
	
	public static class Long extends SimpleNumberType {
		
		public static final Long UNSIGNED = new Long(true);
		public static final Long SIGNED = new Long(false);
		
		private Long(boolean isUnsigned) {
			super(isUnsigned, isUnsigned ? SimpleNumberTypeEnum.UNSIGNED_LONG_INTEGER : SimpleNumberTypeEnum.LONG_INTEGER, "long");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			boolean doUnsigned = t.isUnsigned || isUnsigned;
			switch (t.signed) {
				case BYTE:
					return SWILFragment.simple(doUnsigned ? IS.CULTOI : IS.CLTOI, doUnsigned ? IS.CUITOB : IS.CITOB);
				case SHORT_INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CULTOI : IS.CLTOI, doUnsigned ? IS.CUITOS : IS.CITOS);
				case INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CULTOI : IS.CLTOI);
				case FLOAT:
					return SWILFragment.simple(doUnsigned ? IS.CULTOD : IS.CLTOD, IS.CDTOF);
				case DOUBLE:
					return SWILFragment.simple(doUnsigned ? IS.CULTOD : IS.CLTOD);
			}
			return null;
		}
		
	}
	
	public static class Short extends SimpleNumberType {
		
		public static final Short UNSIGNED = new Short(true);
		public static final Short SIGNED = new Short(false);
		
		private Short(boolean isUnsigned) {
			super(isUnsigned, isUnsigned ? SimpleNumberTypeEnum.UNSIGNED_SHORT_INTEGER : SimpleNumberTypeEnum.SHORT_INTEGER, "short");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			boolean doUnsigned = t.isUnsigned || isUnsigned;
			switch (t.signed) {
				case INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CUBTOI : IS.CBTOI);
				case LONG_INTEGER:
					return SWILFragment.simple(doUnsigned ? IS.CUSTOI : IS.CSTOI, IS.CITOL);
				case FLOAT:
					return SWILFragment.simple(doUnsigned ? IS.CUSTOI : IS.CSTOI, IS.CITOF);
				case DOUBLE:
					return SWILFragment.simple(doUnsigned ? IS.CUSTOI : IS.CSTOI, IS.CITOD);
				case BYTE:
					return SWILFragment.simple(doUnsigned ? IS.CUSTOB : IS.CSTOB);
			}
			return null;
		}
		
	}
	
	public static class Float extends SimpleNumberType {
		
		public static final Float SIGNED = new Float();
		
		private Float() {
			super(false, SimpleNumberTypeEnum.FLOAT, "float");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			switch (t.signed) {
				case INTEGER:
					return SWILFragment.simple(IS.CFTOI);
				case SHORT_INTEGER:
					return SWILFragment.simple(IS.CFTOI, IS.CITOS);
				case LONG_INTEGER:
					return SWILFragment.simple(IS.CFTOI, IS.CITOL);
				case BYTE:
					return SWILFragment.simple(IS.CFTOI, IS.CITOB);
				case DOUBLE:
					return SWILFragment.simple(IS.CFTOD);
			}
			return null;
		}
		
	}
	
	public static class Double extends SimpleNumberType {
		
		public static final Double SIGNED = new Double();
		
		private Double() {
			super(false, SimpleNumberTypeEnum.DOUBLE, "double");
		}
		
		@Override
		public SWILFragment getCastor(TypeDef other) {
			if (!(other instanceof SimpleNumberType)) {
				return null;
			}
			SimpleNumberTypeEnum t = ((SimpleNumberType) other).typeEnum;
			switch (t.signed) {
				case INTEGER:
					return SWILFragment.simple(IS.CFTOI);
				case SHORT_INTEGER:
					return SWILFragment.simple(IS.CFTOI, IS.CITOS);
				case LONG_INTEGER:
					return SWILFragment.simple(IS.CFTOI, IS.CITOL);
				case BYTE:
					return SWILFragment.simple(IS.CFTOI, IS.CITOB);
				case DOUBLE:
					return SWILFragment.simple(IS.CFTOD);
			}
			return null;
		}
		
	}
	
}
