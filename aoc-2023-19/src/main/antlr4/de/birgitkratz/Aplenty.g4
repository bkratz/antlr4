grammar Aplenty;

aplenty: workflows NEWLINE ratings ;

workflows: workflow_line+ ;
ratings: rating_line+ ;

workflow_line: workflow NEWLINE ;
rating_line: rating NEWLINE ;

rating: '{' rating_element (',' rating_element)* '}' ;
rating_element: CATEGORY '=' RATING;

workflow: workflow_name '{' workflow_element (',' workflow_element)* '}' ;
workflow_element: condition              # wfe_condition
                | ACCEPT                 # wfe_accept
                | REJECT                 # wfe_reject
                | workflow_name          # wfe_wfname
                ;
workflow_name: LETTERS ;
condition: CATEGORY COMPARATOR RATING ':' condition_target ;
condition_target: ACCEPT                 # ct_accept
                | REJECT                 # ct_reject
                | workflow_name          # ct_wfname
                ;

COMPARATOR: '>'|'<';
CATEGORY: 'x'|'m'|'a'|'s';
RATING:   NUMBER;
NUMBER:   [1-9][0-9]*;
NEWLINE:  '\r'? '\n';
LETTERS:  [a-z]+;
ACCEPT:   'A';
REJECT:   'R';
WS: [ \t]+ -> skip;