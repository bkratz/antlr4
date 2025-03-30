grammar MorseCodeWithActions;

import MorseAlphabet;

// parser rules - starten mit Kleinbuchstaben
file: line*;
line: (word | WORD_END)* NEW_LINE;
word: LETTER+;

NEW_LINE: '\r'? '\n' {System.out.print("\n");};
WORD_END: '/' {System.out.print(" ");};
LETTER_END: ' ' -> skip;