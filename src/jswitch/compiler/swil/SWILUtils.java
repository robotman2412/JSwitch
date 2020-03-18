package jswitch.compiler.swil;

@SuppressWarnings("unused")
public interface SWILUtils {
	AccumulatorIS IS = new AccumulatorIS();
	
	final class AccumulatorIS {
		
		public final SWILInstruction CITOB = new SWILInstruction("CITOB", 0x00, "convert loaded value from int to byte");
		public final SWILInstruction CUITOB = new SWILInstruction("CUITOB", 0x01, "convert loaded value from uint to byte");
		public final SWILInstruction CITOS = new SWILInstruction("CITOS", 0x02, "convert loaded value from int to short");
		public final SWILInstruction CUITOS = new SWILInstruction("CUITOS", 0x03, "convert loaded value from uint to short");
		public final SWILInstruction CITOL = new SWILInstruction("CITOL", 0x04, "convert loaded value from int to long");
		public final SWILInstruction CUITOL = new SWILInstruction("CUITOL", 0x05, "convert loaded value from uint to long");
		public final SWILInstruction CITOF = new SWILInstruction("CITOF", 0x06, "convert loaded value from int to float");
		public final SWILInstruction CUITOF = new SWILInstruction("CUITOF", 0x07, "convert loaded value from uint to float");
		public final SWILInstruction CITOD = new SWILInstruction("CITOD", 0x08, "convert loaded value from int to double");
		public final SWILInstruction CUITOD = new SWILInstruction("CUITOD", 0x09, "convert loaded value from uint to double");
		
		public final SWILInstruction CBTOI = new SWILInstruction("CBTOI", 0x0A, "convert loaded value from byte to int");
		public final SWILInstruction CUBTOI = new SWILInstruction("CUBTOI", 0x0B, "convert loaded value from unsigned byte to int");
		
		public final SWILInstruction CFTOD = new SWILInstruction("CFTOD", 0x0C, "convert loaded value from float to double");
		public final SWILInstruction CFTOI = new SWILInstruction("CFTOI", 0x0D, "convert loaded value from float to int");
		
		public final SWILInstruction CDTOF = new SWILInstruction("CDTOF", 0x0E, "convert loaded value from double to float");
		public final SWILInstruction CDTOL = new SWILInstruction("CDTOL", 0x0F, "convert loaded value from double to long");
		
		public final SWILInstruction CSTOI = new SWILInstruction("CSTOI", 0x10, "convert loaded value from short to int");
		public final SWILInstruction CUSTOI = new SWILInstruction("CUSTOI", 0x11, "convert loaded value from unsigned short to int");
		public final SWILInstruction CSTOB = new SWILInstruction("CSTOB", 0x12, "convert loaded value from short to byte");
		public final SWILInstruction CUSTOB = new SWILInstruction("CUSTOB", 0x13, "convert loaded value from unsigned short to byte");
		
		public final SWILInstruction CLTOD = new SWILInstruction("CLTOD", 0x14, "convert loaded value from long to double");
		public final SWILInstruction CULTOD = new SWILInstruction("CULTOD", 0x15, "convert loaded value from unsigned long to double");
		public final SWILInstruction CLTOI = new SWILInstruction("CLTOI", 0x16, "convert loaded value from long to int");
		public final SWILInstruction CULTOI = new SWILInstruction("CULTOI", 0x17, "convert loaded value from unsigned long to int");
		
		public final SWILInstruction IXOR = new SWILInstruction("IXOR", 0x18, "XORs loaded value with value at address (required)" /*, IN (adr)*/);
		public final SWILInstruction IXORA = new SWILInstruction("IXORA", 0x19, "XORs loaded value with absolute value" /*, WITH (word)*/);
		public final SWILInstruction IXORW = new SWILInstruction("IXORW", 0x20, "XORs loaded value with absolute value (required)" /*, PTR (var)*/);
		public final SWILInstruction IADD = new SWILInstruction("IADD", 0x21, "adds two words (required)" /*, IN (adr)*/);
		public final SWILInstruction IADDA = new SWILInstruction("IADDA", 0x22, "adds with absolute" /*, WITH (word)*/);
		public final SWILInstruction IADDW = new SWILInstruction("IADDW", 0x23, "adds two words (required)" /*, PTR (var)*/);
		public final SWILInstruction ISUB = new SWILInstruction("ISUB", 0x24, "subtracts value at adr from loaded value (required)" /*, IN (adr)*/);
		public final SWILInstruction ISUBA = new SWILInstruction("ISUBA", 0x25, "subtracts constant from loaded value" /*, WITH (word)*/);
		public final SWILInstruction ISUBW = new SWILInstruction("ISUBW", 0x26, "subtracts variable from loaded value (required)" /*, PTR (var)*/);
		public final SWILInstruction IAND = new SWILInstruction("IAND", 0x27, "ANDs two values (required)" /*, IN (adr)*/);
		public final SWILInstruction IANDA = new SWILInstruction("IANDA", 0x28, "ANDs with constant" /*, WITH (word)*/);
		public final SWILInstruction IANDW = new SWILInstruction("IANDW", 0x29, "ANDs two values (required)" /*, PTR (var)*/);
		public final SWILInstruction IOR = new SWILInstruction("IOR", 0x2A, "ORs two values (required)" /*, IN (adr)*/);
		public final SWILInstruction IORA = new SWILInstruction("IORA", 0x2B, "ORs with constant" /*, WITH (word)*/);
		public final SWILInstruction IORW = new SWILInstruction("IORW", 0x2C, "ORs two values (required)" /*, PTR (var)*/);
		public final SWILInstruction IROTL = new SWILInstruction("IROTL", 0x2D, "rotates value left by one (required)" /*, none*/);
		public final SWILInstruction IROTLM = new SWILInstruction("IROTLM", 0x2E, "rotates value left by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction IROTLA = new SWILInstruction("IROTLA", 0x2F, "rotates value left by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction IROTLW = new SWILInstruction("IROTLW", 0x30, "rotates value left by variable (required)" /*, PTR (var)*/);
		public final SWILInstruction ISHL = new SWILInstruction("ISHL", 0x31, "shifts value left by one (required)" /*, none*/);
		public final SWILInstruction ISHLM = new SWILInstruction("ISHLM", 0x32, "shifts value left by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction ISHLA = new SWILInstruction("ISHLA", 0x33, "shifts value left by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction ISHLW = new SWILInstruction("ISHLW", 0x34, "shifts value left by variable" /*, PTR (var)*/);
		public final SWILInstruction IROTR = new SWILInstruction("IROTR", 0x35, "rotates value right by one (required)" /*, none*/);
		public final SWILInstruction IROTRM = new SWILInstruction("IROTRM", 0x36, "rotates value right by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction IROTRA = new SWILInstruction("IROTRA", 0x37, "rotates value right by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction IROTRW = new SWILInstruction("IROTRW", 0x38, "rotates value right by adr" /*, PTR (var)*/);
		public final SWILInstruction ISHR = new SWILInstruction("ISHR", 0x39, "shifts value right by one (required)" /*, none*/);
		public final SWILInstruction ISHRM = new SWILInstruction("ISHRM", 0x3A, "shifts value right by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction ISHRA = new SWILInstruction("ISHRA", 0x3B, "shifts value right by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction ISHRW = new SWILInstruction("ISHRW", 0x3C, "shifts value right by varialbe" /*, PTR (var)*/);
		public final SWILInstruction IINC = new SWILInstruction("IINC", 0x3D, "increments value" /*, none*/);
		public final SWILInstruction IDEC = new SWILInstruction("IDEC", 0x3E, "decrements value" /*, none*/);
		
		public final SWILInstruction ICOMPI = new SWILInstruction("ICOMPI", 0x3F, "compare two signed integers" /*, IN (adr)*/);
		public final SWILInstruction UICOMPI = new SWILInstruction("UICOMPI", 0x40, "compare signed against unsigned integer" /*, IN (adr)*/);
		public final SWILInstruction UICOMPUI = new SWILInstruction("UICOMPUI", 0x41, "compare two unsigned integers" /*, IN (adr)*/);
		public final SWILInstruction ICOMPIW = new SWILInstruction("ICOMPIW", 0x42, "compare two signed integers" /*, PTR (var)*/);
		public final SWILInstruction UICOMPIW = new SWILInstruction("UICOMPIW", 0x43, "compare signed against unsigned integer" /*, PTR (var)*/);
		public final SWILInstruction UICOMPUIW = new SWILInstruction("UICOMPUIW", 0x44, "compare two unsigned integers" /*, PTR (var)*/);
		public final SWILInstruction ICOMPIA = new SWILInstruction("ICOMPIA", 0x45, "compare two signed integers" /*, WITH (word)*/);
		public final SWILInstruction UICOMPIA = new SWILInstruction("UICOMPIA", 0x46, "compare signed against unsigned integer" /*, WITH (word)*/);
		public final SWILInstruction UICOMPUIA = new SWILInstruction("UICOMPUIA", 0x47, "compare two unsigned integers" /*, WITH (word)*/);
		
		public final SWILInstruction ILOAD0 = new SWILInstruction("ILOAD0", 0x48, "loads value 0" /*, none*/);
		public final SWILInstruction ILOAD1 = new SWILInstruction("ILOAD1", 0x49, "loads value 1" /*, none*/);
		public final SWILInstruction ILOADA = new SWILInstruction("ILOADA", 0x4A, "loads absolute value" /*, VAL (word)*/);
		public final SWILInstruction ILOAD = new SWILInstruction("ILOAD", 0x4B, "loads value at adr" /*, IN (adr)*/);
		public final SWILInstruction ILOADW = new SWILInstruction("ILOADW", 0x4C, "loads value at var" /*, PTR (var)*/);
		public final SWILInstruction IST = new SWILInstruction("IST", 0x4D, "stores value at adr" /*, OUT (adr)*/);
		public final SWILInstruction ISTW = new SWILInstruction("ISTW", 0x4E, "stores value at var" /*, PTR_OUT (var)*/);
		
		public final SWILInstruction LXOR = new SWILInstruction("LXOR", 0x4F, "XORs loaded long value with long value at address (required)" /*, IN (adr)*/);
		public final SWILInstruction LXORA = new SWILInstruction("LXORA", 0x50, "XORs loaded long value with absolute long value" /*, WITH (word)*/);
		public final SWILInstruction LXORW = new SWILInstruction("LXORW", 0x51, "XORs loaded long value with absolute long value (required)" /*, PTR (var)*/);
		public final SWILInstruction LADD = new SWILInstruction("LADD", 0x52, "adds two words (required)" /*, IN (adr)*/);
		public final SWILInstruction LADDA = new SWILInstruction("LADDA", 0x53, "adds with absolute" /*, WITH (word)*/);
		public final SWILInstruction LADDW = new SWILInstruction("LADDW", 0x54, "adds two words (required)" /*, PTR (var)*/);
		public final SWILInstruction LSUB = new SWILInstruction("LSUB", 0x55, "subtracts long value at adr from loaded long value (required)" /*, IN (adr)*/);
		public final SWILInstruction LSUBA = new SWILInstruction("LSUBA", 0x56, "subtracts constant from loaded long value" /*, WITH (word)*/);
		public final SWILInstruction LSUBW = new SWILInstruction("LSUBW", 0x57, "subtracts variable from loaded long value (required)" /*, PTR (var)*/);
		public final SWILInstruction LAND = new SWILInstruction("LAND", 0x58, "ANDs two long values (required)" /*, IN (adr)*/);
		public final SWILInstruction LANDA = new SWILInstruction("LANDA", 0x59, "ANDs with constant" /*, WITH (word)*/);
		public final SWILInstruction LANDW = new SWILInstruction("LANDW", 0x5A, "ANDs two long values (required)" /*, PTR (var)*/);
		public final SWILInstruction LOR = new SWILInstruction("LOR", 0x5B, "ORs two long values (required)" /*, IN (adr)*/);
		public final SWILInstruction LORA = new SWILInstruction("LORA", 0x5C, "ORs with constant" /*, WITH (word)*/);
		public final SWILInstruction LORW = new SWILInstruction("LORW", 0x5D, "ORs two long values (required)" /*, PTR (var)*/);
		public final SWILInstruction LROTL = new SWILInstruction("LROTL", 0x5E, "rotates long value left by one" /*, none*/);
		public final SWILInstruction LROTLM = new SWILInstruction("LROTLM", 0x5F, "rotates long value left by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction LROTLA = new SWILInstruction("LROTLA", 0x60, "rotates long value left by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction LROTLW = new SWILInstruction("LROTLW", 0x61, "rotates long value left by variable" /*, PTR (var)*/);
		public final SWILInstruction LSHL = new SWILInstruction("LSHL", 0x62, "shifts long value left by one (required)" /*, none*/);
		public final SWILInstruction LSHLM = new SWILInstruction("LSHLM", 0x63, "shifts long value left by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction LSHLA = new SWILInstruction("LSHLA", 0x64, "shifts long value left by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction LSHLW = new SWILInstruction("LSHLW", 0x65, "shifts long value left by variable (required)" /*, PTR (var)*/);
		public final SWILInstruction LROTR = new SWILInstruction("LROTR", 0x66, "rotates long value right by one" /*, none*/);
		public final SWILInstruction LROTRM = new SWILInstruction("LROTRM", 0x67, "rotates long value right by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction LROTRA = new SWILInstruction("LROTRA", 0x68, "rotates long value right by adr" /*, AMOUNT_IN (adr)*/);
		
		public final SWILInstruction NICE = new SWILInstruction("NICE", 0x69, "loads 420 as long value" /*, none*/);
		
		public final SWILInstruction LROTRW = new SWILInstruction("LROTRW", 0x6A, "rotates long value right by adr" /*, PTR (var)*/);
		public final SWILInstruction LSHR = new SWILInstruction("LSHR", 0x6B, "shifts long value right by one (required)" /*, none*/);
		public final SWILInstruction LSHRM = new SWILInstruction("LSHRM", 0x6C, "shifts long value right by multiple" /*, AMOUNT (word)*/);
		public final SWILInstruction LSHRA = new SWILInstruction("LSHRA", 0x6D, "shifts long value right by adr" /*, AMOUNT_IN (adr)*/);
		public final SWILInstruction LSHRW = new SWILInstruction("LSHRW", 0x6E, "shifts long value right by varialbe (required)" /*, PTR (var)*/);
		public final SWILInstruction LINC = new SWILInstruction("LINC", 0x6F, "increments long value" /*, none*/);
		public final SWILInstruction LDEC = new SWILInstruction("LDEC", 0x70, "decrements long value" /*, none*/);
		
		public final SWILInstruction LCOMPL = new SWILInstruction("LCOMPL", 0x71, "compare two signed integers" /*, IN (adr)*/);
		public final SWILInstruction ULCOMPL = new SWILInstruction("ULCOMPL", 0x72, "compare signed against unsigned integer" /*, IN (adr)*/);
		public final SWILInstruction ULCOMPUL = new SWILInstruction("ULCOMPUL", 0x73, "compare two unsigned integers" /*, IN (adr)*/);
		public final SWILInstruction LCOMPLW = new SWILInstruction("LCOMPLW", 0x74, "compare two signed integers" /*, PTR (var)*/);
		public final SWILInstruction ULCOMPLW = new SWILInstruction("ULCOMPLW", 0x75, "compare signed against unsigned integer" /*, PTR (var)*/);
		public final SWILInstruction ULCOMPULW = new SWILInstruction("ULCOMPULW", 0x76, "compare two unsigned integers" /*, PTR (var)*/);
		public final SWILInstruction LCOMPLA = new SWILInstruction("LCOMPLA", 0x78, "compare two signed integers" /*, WITH (word)*/);
		public final SWILInstruction ULCOMPLA = new SWILInstruction("ULCOMPLA", 0x79, "compare signed against unsigned integer" /*, WITH (word)*/);
		public final SWILInstruction ULCOMPULA = new SWILInstruction("ULCOMPULA", 0x7A, "compare two unsigned integers" /*, WITH (word)*/);
		
		public final SWILInstruction LLOAD0 = new SWILInstruction("LLOAD0", 0x7B, "loads long value 0" /*, none*/);
		public final SWILInstruction LLOAD1 = new SWILInstruction("LLOAD1", 0x7C, "loads long value 1" /*, none*/);
		public final SWILInstruction LLOADA = new SWILInstruction("LLOADA", 0x7D, "loads absolute long value" /*, VAL (word)*/);
		public final SWILInstruction LLOAD = new SWILInstruction("LLOAD", 0x7E, "loads long value at adr" /*, IN (adr)*/);
		public final SWILInstruction LLOADW = new SWILInstruction("LLOADW", 0x7F, "loads long value at var" /*, PTR (var)*/);
		public final SWILInstruction LST = new SWILInstruction("LST", 0x80, "stores long value at adr" /*, OUT (adr)*/);
		public final SWILInstruction LSTW = new SWILInstruction("LSTW", 0x81, "stores long value at var" /*, PTR_OUT (var)*/);
		
		public final SWILInstruction CALL = new SWILInstruction("CALL", 0x82, "calls subroutine at adr (required)" /*, SUB (adr)*/);
		public final SWILInstruction RET = new SWILInstruction("RET", 0x83, "returns from subroutine" /*, none*/);
		public final SWILInstruction INVOKE = new SWILInstruction("INVOKE", 0x84, "see function calling at beginning of file" /*, FUNC_ID (word), TO (func)*/);
		public final SWILInstruction RETINVK = new SWILInstruction("RETINVK", 0x85, "returns from function" /*, none*/);
		public final SWILInstruction INVOKEI = new SWILInstruction("INVOKEI", 0x86, "calls function in interface (not implemented yet)" /*, FUNC_ID (word), TO (interf)*/);
		
		public final SWILInstruction IPUSH = new SWILInstruction("IPUSH", 0x88, "pushes value onto the stack (required)" /*, none*/);
		public final SWILInstruction IPULL = new SWILInstruction("IPULL", 0x89, "pulls value from the stack (required)" /*, none*/);
		public final SWILInstruction IPUSHA = new SWILInstruction("IPUSHA", 0x8A, "pushes val onto the stack" /*, VAL (word)*/);
		public final SWILInstruction IPUSHM = new SWILInstruction("IPUSHM", 0x8B, "pushes value at adr onto the stack" /*, IN (adr)*/);
		public final SWILInstruction IPOP = new SWILInstruction("IPOP", 0x8C, "discards a value from the stack" /*, none*/);
		
		public final SWILInstruction APUSH = new SWILInstruction("APUSH", 0x8D, "puts an address value onto the stack (see addresses on stack at beginning of file)" /*, none*/);
		public final SWILInstruction APUSH0 = new SWILInstruction("APUSH0", 0x8E, "puts a null address value onto the stack" /*, none*/);
		public final SWILInstruction APUSHW = new SWILInstruction("APUSHW", 0x8F, "puts a pointer to the object instance denoted by this variable onto the stack, or null if not present" /*, PTR (var)*/);
		public final SWILInstruction APULL = new SWILInstruction("APULL", 0x90, "pulls an address from the stack" /*, none*/);
		public final SWILInstruction APOP = new SWILInstruction("APOP", 0x91, "discards an eddress from the stack" /*, none*/);
		
		public final SWILInstruction LPUSH = new SWILInstruction("LPUSH", 0x92, "pushes long value onto the stack (required)" /*, none*/);
		public final SWILInstruction LPULL = new SWILInstruction("LPULL", 0x93, "pulls long value from the stack (required)" /*, none*/);
		public final SWILInstruction LPUSHA = new SWILInstruction("LPUSHA", 0x94, "pushes val onto the stack" /*, VAL (word)*/);
		public final SWILInstruction LPUSHM = new SWILInstruction("LPUSHM", 0x95, "pushes long value at adr onto the stack" /*, IN (adr)*/);
		public final SWILInstruction LPOP = new SWILInstruction("LPOP", 0x96, "discards a long value from the stack" /*, none*/);
		
		public final SWILInstruction REF = new SWILInstruction("REF", 0x97, "adds a reference to value as object instance pointer (implemented by default)" /*, none*/);
		public final SWILInstruction REFPULL = new SWILInstruction("REFPULL", 0x98, "references, but pulls an address from the stack first (implemented by default)" /*, none*/);
		public final SWILInstruction DEREF = new SWILInstruction("DEREF", 0x99, "removes a reference to value as object instance pointer (see: referencing at beginning of file, implemented by default)" /*, none*/);
		public final SWILInstruction DEREFPOP = new SWILInstruction("DEREFPOP", 0x9A, "deref, but pulls an address from the stack first (implemented by default)" /*, none*/);
		public final SWILInstruction DEREFPOPB = new SWILInstruction("DEREFPOPB", 0x9B, "deref, but pulls an address from the stack first, without modifying loaded value (implemented by default)" /*, none*/);
		
		public final SWILInstruction IMULT = new SWILInstruction("IMULT", 0x9C, "multiplies two ints (required)" /*, IN (adr)*/);
		public final SWILInstruction UIMULT = new SWILInstruction("UIMULT", 0x9D, "multiplies two uints" /*, IN (adr)*/);
		public final SWILInstruction IMULTA = new SWILInstruction("IMULTA", 0x9E, "multiplies two ints" /*, WITH (word)*/);
		public final SWILInstruction UIMULTA = new SWILInstruction("UIMULTA", 0x9F, "multiplies two uints" /*, WITH (word)*/);
		public final SWILInstruction IMULTW = new SWILInstruction("IMULTW", 0xA0, "multiplies two ints" /*, PTR (var)*/);
		public final SWILInstruction UIMULTW = new SWILInstruction("UIMULTW", 0xA1, "multiplies two uints" /*, PTR (var)*/);
		
		public final SWILInstruction IDIV = new SWILInstruction("IDIV", 0xA2, "divides two ints (required)" /*, IN (adr)*/);
		public final SWILInstruction UIDIV = new SWILInstruction("UIDIV", 0xA3, "divides two uints" /*, IN (adr)*/);
		public final SWILInstruction IDIVA = new SWILInstruction("IDIVA", 0xA4, "divides two ints" /*, WITH (word)*/);
		public final SWILInstruction UIDIVA = new SWILInstruction("UIDIVA", 0xA5, "divides two uints" /*, WITH (word)*/);
		public final SWILInstruction IDIVW = new SWILInstruction("IDIVW", 0xA6, "divides two ints" /*, PTR (var)*/);
		public final SWILInstruction UIDIVW = new SWILInstruction("UIDIVW", 0xA7, "divides two uints" /*, PTR (var)*/);
		
		public final SWILInstruction LMULT = new SWILInstruction("LMULT", 0xA8, "multiplies two longs (required)" /*, IN (adr)*/);
		public final SWILInstruction ULMULT = new SWILInstruction("ULMULT", 0xA9, "multiplies two unsigned longs" /*, IN (adr)*/);
		public final SWILInstruction LMULTW = new SWILInstruction("LMULTW", 0xAA, "multiplies two longs" /*, PTR (var)*/);
		public final SWILInstruction ULMULTW = new SWILInstruction("ULMULTW", 0xAB, "multiplies two unsigned longs" /*, PTR (var)*/);
		
		public final SWILInstruction LDIV = new SWILInstruction("LDIV", 0xAC, "divides two longs (required)" /*, IN (adr)*/);
		public final SWILInstruction ULDIV = new SWILInstruction("ULDIV", 0xAD, "divides two unsigned longs" /*, IN (adr)*/);
		public final SWILInstruction LDIVW = new SWILInstruction("LDIVW", 0xAE, "divides two longs" /*, PTR (var)*/);
		public final SWILInstruction ULDIVW = new SWILInstruction("ULDIVW", 0xAF, "divides two unsigned longs" /*, PTR (var)*/);
		
		public final SWILInstruction FADD = new SWILInstruction("FADD", 0xB0, "adds two floats" /*, IN (adr)*/);
		public final SWILInstruction FADDA = new SWILInstruction("FADDA", 0xB1, "adds two floats" /*, WITH (word as float)*/);
		public final SWILInstruction FADDW = new SWILInstruction("FADDW", 0xB2, "adds two floats" /*, PTR (var)*/);
		public final SWILInstruction FSUB = new SWILInstruction("FSUB", 0xB3, "subtracts two floats" /*, IN (adr)*/);
		public final SWILInstruction FSUBA = new SWILInstruction("FSUBA", 0xB4, "subtracts two floats" /*, WITH (word as float)*/);
		public final SWILInstruction FSUBW = new SWILInstruction("FSUBW", 0xB5, "subtracts two floats" /*, PTR (var)*/);
		
		public final SWILInstruction FMULT = new SWILInstruction("FMULT", 0xB6, "multiplies two floats" /*, IN (adr)*/);
		public final SWILInstruction FMULTA = new SWILInstruction("FMULTA", 0xB7, "multiplies two floats" /*, WITH (word as float)*/);
		public final SWILInstruction FMULTW = new SWILInstruction("FMULTW", 0xB8, "multiplies two floats" /*, PTR (var)*/);
		public final SWILInstruction FDIV = new SWILInstruction("FDIV", 0xB9, "divides two floats" /*, IN (adr)*/);
		public final SWILInstruction FDIVA = new SWILInstruction("FDIVA", 0xBA, "divides two floats" /*, WITH (word as float)*/);
		public final SWILInstruction FDIVW = new SWILInstruction("FDIVW", 0xBB, "divides two floats" /*, PTR (var)*/);
		
		public final SWILInstruction DADD = new SWILInstruction("DADD", 0xB0, "adds two double precision floats" /*, IN (adr)*/);
		public final SWILInstruction DADDA = new SWILInstruction("DADDA", 0xB1, "adds two double precision floats" /*, WITH (word as float)*/);
		public final SWILInstruction DADDW = new SWILInstruction("DADDW", 0xB2, "adds two double precision floats" /*, PTR (var)*/);
		public final SWILInstruction DSUB = new SWILInstruction("DSUB", 0xB3, "subtracts two double precision floats" /*, IN (adr)*/);
		public final SWILInstruction DSUBA = new SWILInstruction("DSUBA", 0xB4, "subtracts two double precision floats" /*, WITH (word as float)*/);
		public final SWILInstruction DSUBW = new SWILInstruction("DSUBW", 0xB5, "subtracts two double precision floats" /*, PTR (var)*/);
		
		public final SWILInstruction DMULT = new SWILInstruction("DMULT", 0xB6, "multiplies two double precision floats" /*, IN (adr)*/);
		public final SWILInstruction DMULTA = new SWILInstruction("DMULTA", 0xB7, "multiplies two double precision floats" /*, WITH (word as float)*/);
		public final SWILInstruction DMULTW = new SWILInstruction("DMULTW", 0xB8, "multiplies two double precision floats" /*, PTR (var)*/);
		public final SWILInstruction DDIV = new SWILInstruction("DDIV", 0xB9, "divides two double precision floats" /*, IN (adr)*/);
		public final SWILInstruction DDIVA = new SWILInstruction("DDIVA", 0xBA, "divides two double precision floats" /*, WITH (word as float)*/);
		public final SWILInstruction DDIVW = new SWILInstruction("DDIVW", 0xBB, "divides two double precision floats" /*, PTR (var)*/);
		
		public final SWILInstruction ARRLOAD = new SWILInstruction("ARRLOAD", 0xBC, "loads a single word from a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRLOADW = new SWILInstruction("ARRLOADW", 0xBD, "loads a single word from an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		public final SWILInstruction ARRLOADL = new SWILInstruction("ARRLOADL", 0xBE, "loads a long word from a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRLOADLW = new SWILInstruction("ARRLOADLW", 0xBF, "loads a long word from an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		public final SWILInstruction ARRLOADA = new SWILInstruction("ARRLOADA", 0xC0, "loads an object pointer from a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRLOADAW = new SWILInstruction("ARRLOADAW", 0xC1, "loads an object pointer from an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		
		public final SWILInstruction ARRST = new SWILInstruction("ARRST", 0xC2, "stores a single word into a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRSTW = new SWILInstruction("ARRSTW", 0xC3, "stores a single word into an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		public final SWILInstruction ARRSTL = new SWILInstruction("ARRSTL", 0xC4, "stores a long word into a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRSTLW = new SWILInstruction("ARRSTLW", 0xC5, "stores a long word into an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		public final SWILInstruction ARRSTA = new SWILInstruction("ARRSTA", 0xC6, "stores an object pointer into a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction ARRSTAW = new SWILInstruction("ARRSTAW", 0xC7, "stores an object pointer into an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		
		public final SWILInstruction IKVOKEX = new SWILInstruction("ARRSTA", 0xC8, "stores an object pointer into a static array" /*, ARR_PTR (adr), INDEX (adr)*/);
		public final SWILInstruction RETINVKX = new SWILInstruction("ARRSTAW", 0xC9, "stores an object pointer into an instance array" /*, ARR_PTR (var), INDEX (adr)*/);
		
		public final SWILInstruction DBG_STOP = new SWILInstruction("DBG_STOP", 0xFF, "If debugging mode is enabled on the runtime, execution is paused for the thread." /*, none*/);
		
	}
	
}
