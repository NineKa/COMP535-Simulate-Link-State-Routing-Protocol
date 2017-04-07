grammar Graph;

connections : (connection)*;

connection  : left_connection
            | right_connection
            | double_connection
            ;

left_connection     : lhs=IP_ADDRESS LEFT_ARROW weight=NUMBER DASH rhs=IP_ADDRESS;
right_connection    : lhs=IP_ADDRESS DASH weight=NUMBER RIGHT_ARROW rhs=IP_ADDRESS;
double_connection   : lhs=IP_ADDRESS LEFT_ARROW weight=NUMBER RIGHT_ARROW rhs=IP_ADDRESS;

IP_ADDRESS : [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]?;
NUMBER : '0' | ( [0-9][0-9]* );

fragment ESCAPED_QUOTE : '\\"';
QUOTED_STRING :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"';

LEFT_ARROW: '<' ('-')+;
RIGHT_ARROW: ('-')+ '>';
DASH: ('-')+;

NEWLINE : [\r\n] -> skip;
WHITESPACE : [ \t]+ -> skip;
COMMENT : '#' (~('\r' | '\n'))* -> skip;