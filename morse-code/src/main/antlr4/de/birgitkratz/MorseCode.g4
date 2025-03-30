grammar MorseCode;

// parser rules - starten mit Kleinbuchstaben
file: line*;
line: (word | WORD_END)* NEW_LINE;
word: LETTER+;

// lexer rules - Grossbuchstaben
LETTER: ('-'|'.')+;
NEW_LINE: '\r'? '\n';
WORD_END: '/';
LETTER_END: ' ' -> skip;