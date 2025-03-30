/** Grammars always start with a grammar header.
*   This grammar is called ArrayInit and must match the filename: ArrayInit.g4
*/
grammar IntArray;

/** a rule called 'init' that matches comma-sparated values between {...} */
init: ('{' value (',' value)* '}')*;

/** a value can be either a nested array/struct or a simple integer (INT) */
value:  init
      | INT
      ;

// parser rules start with lowercase letters, lexer rules with uppercase
INT: [0-9]+;                 // Define token INT as one or more digits
WS: [ \t\r\n]+ -> skip;      // Define whitespace rule, toss it out