grammar LCD;

// parser rules
file: lcd_numbers*;
lcd_numbers: (line1=lcd_number_line)
             (line2=lcd_number_line)
             (line3=lcd_number_line)
             (empty_line);
empty_line: NEWLINE;
lcd_number_line: (number_parts)+ NEWLINE;
number_parts: NUMBER_PART;

// lexer rules
NEWLINE: '\r'? '\n' ;
NUMBER_PART : (NUMBER_FRAGMENT)(NUMBER_FRAGMENT)(NUMBER_FRAGMENT);

fragment NUMBER_FRAGMENT: ' '|'_'|'|' ;