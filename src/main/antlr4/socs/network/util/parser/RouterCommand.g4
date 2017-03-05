grammar RouterCommand;

command :   cmdAttach
        |   cmdAttachFile
        |   cmdStart
        |   cmdNeighbors
        |   cmd_detect
        |   cmdExit   // TODO : remove this in the future
        ;

cmdAttach : CMD_ATTACH processIP=IP_ADDRESS processPort=NUMBER simulateIP=IP_ADDRESS weight=NUMBER;
cmdAttachFile : CMD_ATTACH path=QUOTED_STRING weight=NUMBER;
cmdStart : CMD_START;
cmdNeighbors : CMD_NEIGHBORS;
cmd_detect : CMD_DETECT simulateIP=IP_ADDRESS;
cmdExit : CMD_EXIT;  // TODO : remove this in the future

IP_ADDRESS : [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]?;
NUMBER : '0' | ( [0-9][0-9]* );

fragment ESCAPED_QUOTE : '\\"';
QUOTED_STRING :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"';

CMD_DETECT : 'detect';
CMD_DISCONNECT : 'disconnect';
CMD_QUIT : 'quit';
CMD_ATTACH : 'attach';
CMD_START : 'start';
CMD_CONNECT : 'connect';
CMD_NEIGHBORS : 'neighbors';

CMD_EXIT : 'exit';  // TODO : remove this in the future

NEWLINE : [\r\n] -> skip;
WHITESPACE : [ \t]+ -> skip;
