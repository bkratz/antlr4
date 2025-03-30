grammar LCDWithActions;

file: lcd_numbers* ;

lcd_numbers
locals [String[] line1, String[] line2, String[] line3]
@after {
System.out.print("Account Number: ");
    for (int i=0; i<$line1.length-1; i++) {
        String n = $line1[i] + $line2[i] + $line3[i];
        int number = switch(n) {
        case " _ | ||_|" -> 0;
        case "     |  |" -> 1;
        case " _  _||_ " -> 2;
        case " _  _| _|" -> 3;
        case "   |_|  |" -> 4;
        case " _ |_  _|" -> 5;
        case " _ |_ |_|" -> 6;
        case " _   |  |" -> 7;
        case " _ |_||_|" -> 8;
        case " _ |_| _|" -> 9;
        default -> -1;
        };
        System.out.print(number);
    }
System.out.println();
}
:
    l1=lcd_line {
    $line1 = $l1.text.split("(?<=\\G.{3})");
    }
    l2=lcd_line {
    $line2 = $l2.text.split("(?<=\\G.{3})");
    }
    l3=lcd_line {
    $line3 = $l3.text.split("(?<=\\G.{3})");
    }
    empty_line;

lcd_line: (NUMBER_PART)* NEWLINE;

empty_line: NEWLINE;

NEWLINE: '\r'? '\n' ;
NUMBER_PART: (NUMBER_FRAGMENT)(NUMBER_FRAGMENT)(NUMBER_FRAGMENT) ;

fragment NUMBER_FRAGMENT: ' '|'_'|'|' ;