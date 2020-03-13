package jswitch.compiler.tokenising;

public enum TokenType {
	SIMPLE, //any old text
	LITERAL, //any literal (strings, boolean, numbers, etc.)
	STRUCTURE, //stuff like: [ ] { } ( ) ; # @ and comment openers
	SPACE, //whitespace and comment content
	SEPERATION,
		/*
		any of these, priority left to right:
		.  ,  >>>=  >>>  >>=  <<=  >>  <<  >=  <=  >  <  ==  =  |=  ||  |  &=  &&  &  !=  !  ?  :  +=  ++  +  -=  --  -  *=  *  /=  /  ^=  ^  %=  %  ::  :
		*/
	NEWLINE, //new line in the file
	KEYWORD
}
